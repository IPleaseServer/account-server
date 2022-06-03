## Auth
### Authorize Teacher
request
```http request
POST /api/v1/account/auth/teacher?identifier=* //교사인증기에서 인증코드를 가져오기위한 key값
```
response
```json5
{ }
```
### Authorize Email
request
```http request
POST /api/v1/account/auth/email?email=*@*.*
```
response
```json5
{ }
```
### Authorize
```http request
GET /api/v1/account/auth/{authCode}
```
```json5
{
  "token": "*****" //token has type and data of Authorization
}
```

## Login
### Generate Login Token
request
```http request
POST /api/v1/account/login
{
  "email": "*@gsm.hs.kr",
  "password": "***"
}
```
response
```json5
{
  "accessToken": "*****",
  "refreshToken": "*****"
}
```

### Refresh Login Token
request
```http request
GET /api/v1/account/login/refresh
{
  "refreshToken": "*****"
}
```
response
```json5
{
  "accessToken": "*****",
  "refreshToken": "*****"
}
```

##Register
### Register Student
request
```http request
POST /api/v1/account/register/student
{
  "name": "**",
  "emailToken": "*****",
  "password": "***",
  "studentNumber": 1101, //1101-3420
  "department": "스마트IOT"
}
```
response
```json5
{
  "accountId": 0
}
```

### Register Teacher
request
```http request
POST /api/v1/account/register/teacher
{
  "name": "**",
  "emailToken": "*****",
  "password": "***",
  "teacherCode": "*****"
}
```
response
```json5
{
  "accountId": 0
}
```

##Profile
### Get My Profile
request
```http request
GRPC getProfileByLoginToken(loginToken: String)
```
response
```json5
{
  "type": "", //TEACHER or STUDENT
  "common": {
    "accountId": 0,
    "name": "**",
    "email": "****",
    "profileImage": "*****" //URL
  },
  "studnet": { //null when type is TEACHER
    "schoolNumber": 1101, //1101-3420
    "department": "스마트IOT"
  },
  "teacher": { //null when type is STUDNET
    //teacher hasn't Public Information
  }
}
```
### Get Profile
request
```http request
GRPC getProfileById(accountId: Long)
```
response
```json5
{
  "type": "", //TEACHER or STUDENT
  "common": {
    "accountId": 0,
    "name": "**",
    "email": "****",
    "profileImage": "*****" //URL
  },
  "studnet": { //null when type is TEACHER
    "schoolNumber": 1101, //1101-3420
    "department": "스마트IOT"
  },
  "teacher": { //null when type is STUDNET
    //teacher hasn't Public Information
  }
}
```
