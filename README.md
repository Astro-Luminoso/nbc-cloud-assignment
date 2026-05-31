# 클라우드 과제 

## Lv 0 버젯 설정

<img width="678" height="806" alt="Screenshot 2026-05-21 at 2 32 21 PM" src="https://github.com/user-attachments/assets/ec7e793e-76e6-48e9-83cb-768a0500ea34" />
 
 과제 요구사항과는 다르게 70달러로 설정 했음


 ## Lv1 내트워크 구축 및 핵심 기능 배포

 과제의 요구사항에 맞게 VPC 생성 및 public Subnet에 EC2를 생성하였으며, EC2 안에 스프링 어플리케이션을 배포한 상태이다.

EC2 IP: 3.34.139.137

로컬에서는 H2 DB를 사용하고 배포환경에서는 MySQL을 사용할 수 있게 프로필을 분리하였습니다.

AOP 기능을 활용하여 Logging을 Controller에서 직접 구현하지 않고 Aspect 클래스를 생성하여 구현하였습니다 (common 패키지 참조)

Actuator 의존성도 추가하여 `/actuator/health` 접속이 가능합니다.

<img width="520" height="554" alt="Screenshot 2026-05-26 at 10 20 32 AM" src="https://github.com/user-attachments/assets/1a735f58-794a-46e9-8fca-bf850699a6b4" />


## Lv2.DB분리 및 보안 연결하기

해당과제에서는 처음 배포 했었을 때부터 RDS를 이용하고 있었다.

<img width="1633" height="250" alt="Screenshot 2026-05-26 at 10 30 17 AM" src="https://github.com/user-attachments/assets/218ab3ae-25f3-4018-8a72-32c371280389" />

시큐리티 그룹 설정에 있어서는 과제하는 동안에는 IntelliJ를 통해서 DB에 직접 접속하고 싶었기 때문에 완전히 개방해놨었다. 현재 지금은 과제에 사용중인 EC2 보안그룹 ID를 등록해 놓은 상태이다.

<img width="1591" height="260" alt="Screenshot 2026-05-26 at 10 36 17 AM" src="https://github.com/user-attachments/assets/4a08c219-4778-4d74-bd0d-f73cf4699607" />

`/actuator/info` 를 통해 값이 조회되도록 설정했습니다.

<img width="637" height="535" alt="Screenshot 2026-05-26 at 10 20 47 AM" src="https://github.com/user-attachments/assets/5f809c62-9242-4ff2-a46b-807476e04b37" />

요구사항에는 info 조회시 팀 이름이 나올 수 있게 하라고 되어있었긴 하나 좀더 어플리케이션에 대한 정보를 반환하는게 맞다고 생각이 들어서 위와 같은 값을 반환하게 구현하였다.

## Lv3 프로필 사진 기능 추가와 권한 관리

강의대로 했다가는 Parameter Store에서 환경변수를 조회하려고 할때 EC2의 IAM ROLE 권한이 아닌 ACCESS_KEY 및 SECRET_KEY를 우선적으로 이용하려고 하여 어플리케이션이 실행되지 않는 애로사항이 발생했다.

그래서 이전 강의에서 만들었던 S3정책을 활용 및 수정하여 AmazonSSMReadOnlyAccess 정책과 같이 묶어서 역할을 하나 만들었다.

<img width="1282" height="599" alt="Screenshot 2026-05-26 at 10 59 14 AM" src="https://github.com/user-attachments/assets/c9b35eee-b3d1-4b4d-92b2-a7328caab4fa" />

<img width="1562" height="692" alt="Screenshot 2026-05-26 at 10 59 55 AM" src="https://github.com/user-attachments/assets/39bd1d33-4e27-401d-8476-8224cb5457a4" />

위의 스크린샷은 이미지를 업로드 했을때와 다운로드 했을 때의 결과다. 아래 PresignedURL을 접속하면 아마 사진이 나올 것이다. 보잘 것 없는 사진이여도 저작권은 나에게 있다.

https://nbc-cloud-assignment-hyang-bucket.s3.ap-northeast-2.amazonaws.com/uploads/d9f09844-2360-4033-ad76-d56f0d45ea2f_IMG_2261.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEDAaDmFwLW5vcnRoZWFzdC0yIkgwRgIhAOx5oxmOrfUNZkqpKgCbZ0WfVnLH%2FUhUOQ8rMzNKg3mDAiEA3QERjkib4nluwXD1kaJ2l5yYmYnFGUt8xlVJXPqvPUMq1AUI%2Bf%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARAAGgwzNTMyNTEzOTM0NjUiDLxrvu%2FmYCqvRcx%2FIyqoBSK%2BqcoDc%2B1i7TwJlritO5oa5KjHcRfVPnP8cZffFlRMXoOXqGf9uMILxAvHDTbqGCJT7nCBzdL3rsftQ3PxfdbGlA8zK1sDUftlaN9gAYYFDp%2BHhtfcNMyQYhz36MGEkpe8YYMvSYJ0bdp2ui166eWvPT0ltPI%2BgxDIGpgDSVPXNnQn8EEq1yJF3foiWWeZ0rIZ1FTrDVQomSKH9XEIjKVHDfhSllxTEvlXc7rxAzC%2F%2F93%2FmWuC5u9Xqo9Ze%2BlOzSQfX6bhcSNFsN0RWsOkT3adsxgD7gqAiASovX9tr0PKDeg65a5HHEqTY8s1gSP9%2Bp3Rqy1CXMeyI1LMk%2BSFGFB%2BaWaWFh3f4KgJl20Mc3hgh7rAvICL1Gb8sIxY04Q6%2FSm%2FVgZUQTSx2qIQsZTwl8PPeSAYPOangcx%2BEhNb%2Fi4ZkopLfKe2Kyy%2F11Lywh4xy270TYHIBQjykcJ3S50pT5XG4lINYsEbygNocOzKASIhmgYiMKpbbPz5T0zntecTP%2Fi0QgF1cfarJrlxWbZVR%2F%2BrzqVyS3qrFTLI3IG4xwqU6yZ%2FmUVknk3yPmS25DawEPRfLFwLSZtjfw4%2FfHSI2IehCaROBjPjecO%2FzriD0NmcJ7pkWXAmsmCK8Nz0tzWEvO0uHw%2FOnLaQVjsU4kQRTciAkpFmIsTLRcYSkloMbGtR00RhDfHMjGI%2Be7VsQCfyc4F6fvKxuxSMaVVWdxv2KFX0kX4yJT%2FcG2%2FgPOjErbn6vW0ld933S0%2BaO7c23ZdpjVjqQzcwlcC%2BwvTknqJkve6hfT6T6f%2BVo9wIWBnG1S6IgRGXkdOp8p7AdcA%2FOiome1sxF4JNt0q5KpY88MaGfSAQsZvk9NWo2frLE6fTVYY3gTdhtrzPi%2B4K1mhN0J%2B9I9R9ikkwSlcZMNy78dAGOrABSGuYErNde8IGOqRVNBwj4lI1UJpdupQlJtUyHOH8DZ07R%2FDn%2B2z4AWBl2FgaNJZe0NEUJaBOsXJqYlU0NC35y8rh2zwXuvvn2mHVibkoEpukeFNORzQb3QOt3rKq7jj828JsJdrcvbrNyujnVL40Vw%2BvbzoFiiOKWVCDZe3oa6n4CPaU8QvNXACma6ERKZG9rii7grhAF44wYS64Vm5qj12NrGwkcvFgYNlmtPIHjn8%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20260531T163544Z&X-Amz-SignedHeaders=host&X-Amz-Credential=ASIAVEP3MP644WA7P67X%2F20260531%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Expires=604800&X-Amz-Signature=4d4ffc3d7c420d433fcb42222b17df16aef72c244e60acf1a4d2868a6ffb5df3

플래시 터트린거 아니다. 오해 하지 마시길...

--- 필수 과제 끝 ---

---

## Lv.4 Docker & CI/CD 파이프라인 구축 

도커를 이용한 배포및 cicd 파이프라인 구축을 실시하였다.

(deploy.yml은 리포지토리에서 직접 확인하시면 됩니다.)

<img width="938" height="364" alt="Screenshot 2026-06-01 at 1 18 57 AM" src="https://github.com/user-attachments/assets/cdc37a14-c87b-4c04-bbf1-27ae9a0e3320" />

<img width="1902" height="92" alt="Screenshot 2026-06-01 at 1 20 05 AM" src="https://github.com/user-attachments/assets/61dc83f2-92ea-40fd-80e3-90c28b7cac78" />

## Lv.5 고가용성 아키텍처와 보안 도메인 연결 (ALB + ASG + HTTPS)
