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

https://nbc-cloud-assignment-hyang-bucket.s3.ap-northeast-2.amazonaws.com/uploads/d9f09844-2360-4033-ad76-d56f0d45ea2f_IMG_2261.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjELf%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaDmFwLW5vcnRoZWFzdC0yIkgwRgIhANa%2BgV%2FnD0lKWF3tg8IjH4hi%2FGNJSoCMsqIYS70Ta9zIAiEAwDInjZNvSrCDesYRs6IyojrhTg5XWzflAmUxSQoAJyMq0gUIgP%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARAAGgwzNTMyNTEzOTM0NjUiDBfxswSFr42U6fdYoyqmBbkquU%2B3Vqq1Hd%2Fx4r9%2Bg7NH5Qcqe2MUffAGcGS%2FFl2ZXq8O4XI%2F3agI%2FLk3vb%2BT%2FFPXEQQ9LIS79FS%2BYAGcCdGNyR2gnn9BiYZ0c3kE1%2BNeTXnJ3XPWQnOfkchoBnWFAMHeKR4milKiHCimiCUs12UiM6qPS7Je9UdC89SW6ZfxK1bclpkpMOEUWT34fHa2Ey9cQg1ORsNx9S1xCHXzoGffEaAwNjynLc68RIS1akuc7PeClge8unns6ExmC%2FB%2FbE%2FHtNWvCTuYyzqzt%2BbLRYJYDZ%2FyLH30kQRBdDFHeyehcQOGDnfTC6hXC1PUICpCkdzFSgH8f8KhW4oo%2FO8jZTfeOCKebqHT4XFu7daV4sug86JHG52pWVFQBDGiQFwua%2FqFgjCi36wwsLsiCsRti1WFOlz17PCiNTBPAoyfTakx9Kc%2FNY0syTKDs%2Bbd1%2FOaRPCLo3bJQnGzuO12scK5WSCrLjmVJRzMLGRAPwGKD2Oc4oupnSmONryYXaFvASPz5NxorjDAojOpVj7r4bA5G6NrJhr8IVa2%2FUcs1AXB3cQAobLwxOGkvo8M4XUMuYv39Y7jRwbv0Io2URbemkRnzLFFX35m90M4stvEXthZCwvkAw9ZtQv38kb1OzwogUfbXgFSgY%2F0VFq7FR7D25gWZiJbXZmV2%2FbZNhv0e5Pgxp8nb9bhFFdDztxQzP6%2BDLc8HwNO9E%2BlcwSK0Kg%2Brxr4gMcOtE65%2FU7kW%2F3EAMyfIN%2BCqpD7xWHVcAldV05yWgmROYGYUUEsX0SOxsIxZ5vCaeZH8Xci6yuCIGxzl5FMXI8%2F%2BFaHEMt3EwTg%2B%2Brx9ajZa5MgcolpU6oXVWlqJVlUoNVEUlLzHlIFv1K9qYXGxlP7lfybDMQuDNcsIf%2BvFW1SxBsq5jUJmDDM7tbQBjqwAXm6LH%2F8D5KDLMCbvWD%2BTfPKrb0i6UyQttoHCapyZS0CNXbF5QTwQhYGvIov%2Fqrl5xUOWaBYAttJ9QSq7Y8rL%2FjUCypmJ4rkJbXJjCh4%2B%2BqX787QaXS67%2F%2BDlZv0GaiBeD2t8BqDOKA6RzfjTp7i6W%2F8lz7xtrm%2BCR1XNtGUsPgFhgpDcvpwFqhdFehDZwfy%2BPoZaW4w8LJZ3bPzxZVPOGazKOU6t0pPx6mqK0AmJiNS&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20260526T153324Z&X-Amz-SignedHeaders=host&X-Amz-Credential=ASIAVEP3MP642B36QFI3%2F20260526%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Expires=604800&X-Amz-Signature=48b63c308cbc7c948a53bfe8008e2466702cfdb1dc7759b209746fc3a96e94b8

플래시 터트린거 아니다. 오해 하지 마시길...

--- 필수 과제 끝 ---

---
