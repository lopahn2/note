# Git Branch

깃의 브랜치는 커밋 사이를 이동할 수 있는 포인터라 보면 됩니다.  

실제로 Git의 브랜치는 어떤 한 커밋을 가리키는 40글자의 SHA-1 체크섬 파일에 불과하고, 새로 한 브랜치를 만드는 것은 41바이트(40글자의 체크섬과 줄 바꿈 문자) 크기의 파일을 하나 만드는 것입니다.  

## Branch 기본 활용

```shell
git checkout -b testing # 브랜치를 만들고 이동
vim test.rb
git commit -a -m 'made a change'

git checkout master
vim test.rb
git commit -a -m 'made a change'
```

![](./assets/images/advance-master.png)  

HEAD 포인터는 현재 작업중인 브랜치를 가르키며, 각 브랜치는 현재 작업중인 커밋 체크섬을 가르키고 있습니다.  

현재 상황에서, 하나의 커밋에서 분기된 두 개의 브랜치에서의 독립적인 작업 때문에 커밋 히스토리가 분기된 것을 확인할 수 있습니다.  

```shell
git log --oneline --decorate --graph --all


* c2b9e (HEAD, master) made other changes
| * 87ab2 (testing) made a change
|/
* f30ab add feature #32 - ability to add new formats to the
* 34ac2 fixed bug #1328 - stack overflow under certain conditions
* 98ca9 initial commit of my project
```

### Branch 관리 CLI

```shell
git branch # branch 목록 확인
git branch -v # branch 별 마지막 커밋 메시지 표시

# 현재 브랜치 기준 Merge된 브랜치와 아닌 브랜치 필터링
git branch --merged # merge 됐기 때문에 -d 옵션으로 삭제 가능
git branch --no-merged # merge가 안됐기 때문에 -D 옵션으로 강제 삭제 필요
```

## Merge 기초

브랜치 merge는 보통 아래의 순서대로 진행됩니다.

1. 운영 브랜치에서 issue 브랜치 분기
2. issue 브랜치 작업 중, 운영 브랜치의 HotFix 작업 필요
3. HotFix 브랜치 분기 후, 운영 브랜치와 병합
    - 이때에는 같은 조상 커밋을 바라보고 있기에 `Fast-forward` 병합이 됨
    - 이때 별도의 병합된 commit 이 생성되지 않음 (c4)
4. issue 브랜치를 운영 브랜치에 병합 시, 두 브랜치가 가르키는 커밋이 공통 조상이 아니므로 `3-way Merge`를 진행
    - `Common Ancestor Commit`과 각 브랜치가 가르키는 커밋 두 개를 사용해 Merge를 하는 방식을 `3-way Merge` 라고 함
    - 이때 별도의 병합된 commit이 생성됨 (c6)

![](./assets/images/basic-merging-2.png)

```shell
# 1~2
git checkout -b iss53
vim index.html
git commit -a -m 'added a new footer [issue 53]'

# 3
git checkout master
git checkout -b hotfix
vim index.html
git commit -a -m 'fixed the broken email address'

[hotfix 1fb7853] fixed the broken email address
 1 file changed, 2 insertions(+)

git checkout master
git merge hotfix

Updating f42c576..3a0874c
Fast-forward
 index.html | 2 ++
 1 file changed, 2 insertions(+)

git branch -d hotfix

# 4
git checkout iss53
vim index.html
git commit -a -m 'finished the new footer [issue 53]'

[iss53 ad82d7a] finished the new footer [issue 53]
1 file changed, 1 insertion(+)

git checkout master
git merge iss53

Merge made by the 'recursive' strategy.
index.html |    1 +
1 file changed, 1 insertion(+)

git branch -d iss53
```

## Branch Workflow
가장 대표적인 Branch Workflow 하나를 살펴봅시다.  

안정적인 버전의 코드, 혹은 배포 후 운영 단계에 있는 코드를 master(main) 브랜치에서 관리하고, 개발을 진행하고 안정화 단계에 있는 코드는 develop(next) 브랜치에서 관리하는 플로우입니다.  

여기에서 안정적인 버전의 브랜치를 `Long-Running Branch`라 하고 특정 주제나 작업을 위해 만드는 분기 브랜치를 `Topic Branch`라고 합니다.  

다시 말해서 토픽 브랜치에서 공격적으로 여러 작업을 진행해본 뒤에, 안정성이 확보되면 Long-Running 브랜치에 병합하는 것입니다.  

## Remote Branch

리모트 Refs는 리모트 저장소에 있는 포인터 레퍼런스입니다.  

`git remote show [remote]`명령으로 모든 리모트 브랜치와 그 정보를 볼 수 있습니다.  

### Remote Tracking Branch

```shell
commit 9f55ef8e9056b9883fec8a1581fcfa8b9a696195 (HEAD -> main, origin/main)

# 여기서 origin/main 이 remote tracking branch이다.
```

리모트 트랙킹 브랜치는 리모트 브랜치를 추적하는 로컬에 위치한 브랜치로, 임의로 변경할 수 없으며 리모트의 브랜치의 업데이트 내용에 따라서 자동으로 갱신됩니다.  

![](./assets/images/remote-branches-3.png)

#### 브랜치 추적

리모트 트랙킹 브랜치를 로컬 브랜치로 `checkout`하면 자동으로 `Traking Branch`가 생성됩니다.  

트랙킹하는 대상 브랜치를 `Upstream Branch`라고 부르고, 트랙킹 브랜치는 리모트 브랜치와 직접적인 연결이 돼있는 로컬 브랜치입니다.  

트랙킹 브랜치에서 `git pull`을 하면 Upstream branch로 부터 fetch와 merge를 수행합니다.  

```shell
git checkout -b <local-name> <remote>/<branch> # 수동 생성
git checkout --track <remote>/<branch> # 로컬 이름 자동 생성
git branch -u <remote>/<branch> # 기존 브랜치에 트랙킹 설정 추가

git push origin --delete <remote-branch-name> # 리모트 브랜치 삭제
```

## Rebase

Rebase는 분기된 브랜치를 선형으로 만들어 Merge 시 Fast-forward가 되도록 만드는 병합 방법입니다.  

![](./assets/images/basic-rebase-2.png)  

Merge만 한다면 위 그림과 같겠으나, Rebase를 하면
1. 두 브랜치가 나뉘기 전인 공통 커밋으로 이동
2. 공통 커밋부터 지금 checkout한 브랜치가 가리키는 커밋까지 diff를 차례로 만들어 임시 저장
3. 체크아웃 브랜치가 합쳐질 브랜치가 바라보는 커밋을 가리키게 하고 아까 저장한 diff를 차례대로 적용

![](./assets/images/basic-rebase-3.png)

이후 master 브랜치를 fast=forward 시키면 됩니다.  

### 언제 쓰나요?

Merge든 Rebase든 둘 다 합치는 관점에서는 다를게 없지만, Rebase가 좀 더 깨끗한 히스토리를 만듭니다.  

Rebase는 보통 리모트 브랜치에 커밋을 깔끔하게 정리하기 위해 로컬의 커밋을 정리하는 용도로 사용됩니다.  

![](./assets/images/interesting-rebase-1.png)  

위와 같은 상황에서, 브랜치를 깔끔하게 정리 후 푸시를 하고 싶다고 가정해봅시다.  

이때 server branch는 그대로 두고, client만 master에 옮기려 할 때, `--onto` 옵션을 통해 client branch와 master 브랜치를 선형으로 만들 수 있습니다.  

```shell
git rebase --onto master server client
git checkout master
git merge client
```

![](./assets/images/interesting-rebase-3.png)  

이후 server branch를 master branch에 병합하고 싶으면 rebase를 해주면 됩니다.  

```shell
git rebase master server
git checkout master
git merge server
```

![](./assets/images/interesting-rebase-5.png)  


> [!WARNING]  
> **이미 공개된 저장소에 push 한 커밋을 Rebase 해선 안됩니다.**  
> A가 merge한 커밋을 리모트 레포지토리에 Push한 뒤, Merge했던 것을 되돌려 다시 Rebase를 했다고 해봅시다.  
> B는 이 사실을 모르고 A가 push 한 리모트 레포지토리를 pull 받은 뒤 작업을 한다 했을 때 포함돼선 안되는 커밋(C4, C6)가 남게 됩니다.  
> 이후 B가 작업 후 다시 Push를 하게 되면 아래 그림과 같이 엉망진창의 commit history가 남게 됩니다.  
> ![](./assets/images/perils-of-rebasing-4.png)
> 따라서 `git pull`을 실행할 때 기본적으로 rebase가 되도록 설정을 추가해 안전히 히스토리를 관리해야 합니다.  
> ```shell
> git config --global pull.rebase true
> ```

> [!TIP]  
> **Rebase와 Merge는 구분돼야 합니다.**  
> 결론적으로 Rebase는 로컬의 커밋 히스토리를 깔끔하게 정리할때 사용하면 좋습니다만, 무엇이 더 좋다는 정답이 없습니다.  
> `커밋 히스토리`를 작업한 내용의 기록으로 보는 관점과 프로젝트가 어떻게 진행됐나로 보는 관점에서, 커밋 하나 하나가 유의미한 단위로 떨어지도록 관리하는 수준에서 위 두 가지를 잘 섞어서 사용해야겠다는 생각입니다.  

