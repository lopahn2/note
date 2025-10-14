# MCP_Usage

Claude Desktop과 같은 MCP Host가 MCP Server와 통신하는 기본적인 서비스 흐름은 아래와 같습니다.  

![](./assets/images/mcp_flow.png)

하지만 이 구조에서 벗어나, 조금 더 일반적으로 사용할 수 있는 구조를 고민해보면,  
MCP Client (브릿지 서버) 를 직접 구현해볼 수 있을 것이다.

사용자 -> 프론트 -> LLM에 프롬프트 질의 ( 여기서 fucntion calling 으로 사용자가 사용할 tool을 선택할 수 있다고 한다. )
-> MCP Client에 해당 내용 전달 -> MCP Server로 요청

우리는 여기서 MCP Client과 MCP Server를 모두 만들어서 실습해보도록 하자.

```
// LLM에게 전달
const response = await client.chat.completions.create({
  model: "gpt-4o-mini",
  messages: [
    { role: "system", content: "You can call these tools: get_purchase_history, get_user_profile" },
    { role: "user", content: "회원 123의 구매 내역 알려줘" },
  ],
});

// LLM이 생성한 tool_call 감지
const toolCall = response.choices[0].message.tool_call; // e.g. get_purchase_history(123)
if (toolCall) {
  // 브리지 서버가 내부적으로 function spec 찾아 실행
  const spec = toolRegistry[toolCall.name];
  const result = await callMCPServer(spec, toolCall.arguments);
}

```