## 🚘보초 운전🚘
#### 여러분의 든든한 가이드, 보초 운전
 <img src="https://github.com/user-attachments/assets/48f4100d-6bbb-4cf5-9215-a4820a2d4bb1"></br>
운전, 처음이라 떨리시죠? 걱정 마세요! 보초 운전 커뮤니티가 있습니다. </br>
여긴 초보 운전자들이 모여, 기초부터 안전한 운전 팁까지 서로 공유하고 성장하는 공간이에요. </br>
함께라면 첫 운전도 두렵지 않아요! 운전, 같이 즐겨봐요! 🚗💨</br></br>

## Info
● 프로젝트 브로셔 : <a href="https://dolphin-pc.notion.site/8-13-21-be75fecc932b4a8b88d86d2b1835252c?pvs=4"><b>보초운전 브로셔</b></a></br>
● 배포 링크 :  https://www.bocho.p-e.kr/</br>
● 팀 노션 :  <a href="https://dolphin-pc.notion.site/e4ebb89570f24cdab88acbaf40f86f81?v=aad47b31de7b4ad0814f1604b1a6972d&pvs=74"><b>보초운전 노션</b></a></br>
#### 📆Period 
2024.07.19 ~ 2024.08.15</br></br></br>

### 🚜Project Members
<table>
  <tr>
    <td align="center"><a href="https://github.com/Dolphin-PC"><b>박찬영</b></a><br /></td>
    <td align="center"><a href="https://github.com/hyeon9808"><b>홍채현</b></a><br /></td>
    <td align="center"><a href="https://github.com/whtndls"><b>조수인</b></a><br /></td>
    <td align="center"><a href="https://github.com/young219257"><b>김영아</b></a><br /></td>
    <td align="center"><a href="https://github.com/hyoyeolking"><b>전효열</b></a><br /></td>
  </tr>
  <tr>
      <td align="center"><img src="https://github.com/user-attachments/assets/683f52f8-d8e4-4e9e-ac1f-a5ed7d4b032a" width="130px;" alt=""/><br /><sub></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/a4aa44bc-4d59-46e7-8b74-b18d94a3186e" width="130px;" alt=""/><br /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/03eb0bf2-0ac1-4c7f-8f2d-06243c5be806" width="130px;" alt=""/><br /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/4bb46790-6183-44cc-b51b-270d36c71710" width="130px;" alt=""/><br /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/9ebc81f2-52ba-4b97-b6d3-07c4c21fe1f0" width="130px;" alt=""/><br /></td>
    </tr>
  <tr>
    <td align="center">FrondEnd/Leader</td>
    <td align="center">FrondEnd</td>
    <td align="center">BackEnd/Vice Leader</td>
    <td align="center">BackEnd</td>
    <td align="center">BackEnd</td>
  </tr>
</table>

## Service Architecture
수인님~
## Backend 주요 기술
1. 로그인(OAuth + refreshToken)
2. 웹소켓
3. 이미지S3
## ⚠️Trouble Shooting
### 🚨  **issue 1**

**🔐로그인 AccessToken, RefreshToken 발급**

로그인 시 accessToken을 발급할 때 loginFilter에서 token을 발급하지 않고 controller에서 직접 accessToken을 발급하고 있었다. 

❓ cause

클라이언트가 로그인 요청을 보내면 loginFilter를 거치지 않고 바로 controller로 가는 문제가 발생되고 있었다.

❗ solution
`this.setFilterProcessesUrl("/signin");`

위의 코드를 통해 `UsernamePasswordAuthenticationFilter`가 로그인 요청을 처리할 URL을 `/signin`으로 지정합니다. 이 설정으로 인해, 클라이언트가 `/signin` 경로로 로그인 요청을 보낼 때 해당 필터가 이 요청을 가로채어 처리하도록 설정됩니다.
</br></br>

</br>

### 🚨  **issue 2**

**📷이미지 업로드 관련 오류**

이미지 업로드를 위해 `form-data`를 통해 데이터를 매핑하려고 할 때, 해당 필드에 접근하지 못해 커뮤니티 글이 작성되지 않는 오류가 발생했습니다.

##### ❓ cause

`RequestDto` 클래스에 **@Getter** 어노테이션이 없어서, Spring Boot가 클라이언트가 보낸 데이터를 해당 필드에 접근하여 매핑하지 못했습니다.

- Spring은 데이터 바인딩 시, 내부적으로 해당 필드의 Getter 메서드를 호출하여 값을 읽어오고, 또한 객체를 JSON이나 다른 형식으로 변환할 때도 Getter를 사용합니다.

##### ❗ solution

`RequestDto` 클래스에 **@Getter** 어노테이션을 추가하여 각 필드에 대한 Getter 메서드를 생성합니다. 이를 통해 Spring이 클라이언트로부터 전송된 데이터를 올바르게 매핑할 수 있도록 처리합니다.

