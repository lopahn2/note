# 작업 목록 확인하기

```
- [x] #739
- [ ] https://github.com/octo-org/octo-repo/issues/740
- [ ] Add delight to the experience when all tasks are complete :tada:
```

> 이렇게 나와요!

- [x] #739
- [ ] https://github.com/octo-org/octo-repo/issues/740
- [ ] Add delight to the experience when all tasks are complete :tada:

# 이모지 사용하기
```
:grinning:
:grin:
:joy:
```

> 이렇게 보여요!
>> [이모지 모음 사이트](https://github.com/ikatyang/emoji-cheat-sheet/blob/master/README.md)

:grinning:  
:grin:  
:joy:

# 각주도 넣어 볼까요?
```
Here is a simple footnote[^1].

A footnote can also have multiple lines[^2].

[^1]: My reference.
[^2]: To add line breaks within a footnote, prefix new lines with 2 spaces.
  This is a second line.
```

> 이렇게 보여요!
>> 1. 각주의 위치는 각주가 렌더링될 위치에 영향 X => 각주는 markdown의 하단에 렌더링됨.
>> 2. 각주는 wiki에서 지원되지 X

Here is a simple footnote[^1].

A footnote can also have multiple lines[^2].

[^1]: My reference.
[^2]: To add line breaks within a footnote, prefix new lines with 2 spaces.
  This is a second line.

# 경고도 할 수 있어요

```
> [!NOTE]
> Useful information that users should know, even when skimming content.

> [!TIP]
> Helpful advice for doing things better or more easily.

> [!IMPORTANT]
> Key information users need to know to achieve their goal.

> [!WARNING]
> Urgent info that needs immediate user attention to avoid problems.

> [!CAUTION]
> Advises about risks or negative outcomes of certain actions.

```

> [!NOTE]
> Useful information that users should know, even when skimming content.

> [!TIP]
> Helpful advice for doing things better or more easily.

> [!IMPORTANT]
> Key information users need to know to achieve their goal.

> [!WARNING]
> Urgent info that needs immediate user attention to avoid problems.

> [!CAUTION]
> Advises about risks or negative outcomes of certain actions.

# 표를 만들어 봅시다

```
| Left-aligned | Center-aligned | Right-aligned |
| :---         |     :---:      |          ---: |
| git status   | git status     | git status    |
| git diff     | git diff       | git diff      |
```

> 이렇게 보여요!
>> 1. 2행의 파이프에 정렬 기준을 정할 수 있음

| Left-aligned | Center-aligned | Right-aligned |
| :---         |     :---:      |          ---: |
| git status   | git status     | git status    |
| git diff     | git diff       | git diff      |

# 비밀스럽게 접어보기
```
<details>

<summary>Tips for collapsed sections</summary>

### You can add a header

You can add text within a collapsed section.

You can add an image or a code block, too.

</details>
```

> 이렇게 보여요!
>> 1. <details open> 으로 설정하면 기본으로 열린 상태
>> 2. <summary> 에 값이 열리기 전 보이는 값

<details>

<summary>Tips for collapsed sections</summary>

### You can add a header

You can add text within a collapsed section.

You can add an image or a code block, too.

</details>