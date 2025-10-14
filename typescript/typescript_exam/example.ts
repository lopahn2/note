// 원시 Type의 종류
// null undefined boolean string number bigint symbol

// let let type = hell; -> 구문 오류
console.blub("Nothing is worth more than laughter"); //-> 타입 에러

let lastName = "king";
lastName = true; // assignability ( 변수의 초기값이 변수의 타입을 결정, 다른 타입을 재할당하려 할 때 에러 발생 )
// TS2322: Type boolean is not assignable to type string

// 기본적으로 변수는 any 타입
let rocker: string; // 변수의 타입 할당
rocker = "Joan Jett";

let rapper = "Queen Latifah";
rapper.length;

rapper.push('!'); // 해당 타입이 해당 메서드를 갖고 있는지 아닌지 컴파일러가 알고 있음

let cher = {
    firstName: "Cherilyn",
    lastName: "Sarkisian",
};

cher.middleName; // 해당 변수가 값을 갖고 있는지 알고 있음