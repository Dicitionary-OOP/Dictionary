# Cách phát triển dự án

[View English version/Xem bản tiếng Anh](CONTRIBUTING.md)

## Cấu trúc dự án

Dự án được chia thành 3 modules: `base`, `cli` và
`graphic`.

- Module `cli` sẽ được biên dịch thành file JAR của
  phần giao diện CLI.
- Module `graphic` sẽ được biên dịch thành file JAR của
  phần giao diện GUI.
- Module `base` chứa các chức năng chung mà cả hai
  module trên đều cần dùng tới (các chức năng lõi của
  phần mềm).

Cấu trúc thư mục và file của dự án như sau, trong đó
`x` nghĩa là một file tên là x, còn `[y]` nghĩa là
một thư mục tên là y:

- `[base]`
    - `[src]`
        - `[main/java/dictionary/base]`
            - `...` (Toàn bộ code của module **base**)
    - `[target]`
        - `base-1.0.jar` (JAR file của module **base**)
    - `pom.xml` (Thông tin của module **base**)

- `[cli]`
    - `[src]`
        - `[main/java/dictionary/cli]`
            - `...` (Toàn bộ code của module **cli**)
    - `[target]`
        - `cli-1.0.jar` (JAR file của phần giao diện CLI)
    - `pom.xml` (Thông tin của module **cli**)

- `[graphic]`
    - `[src]`
        - `[main/java/dictionary/graphic]`
            - `...` (Toàn bộ code của module **graphic**)
    - `[target]`
        - `graphic-1.0.jar` (JAR file của phần giao diện GUI)
    - `pom.xml` (Thông tin của module **graphic**)

## Quy định về Git/GitHub

**Bạn không được phép pull và push trực tiếp lên repo chính của
dự án.**

Vào trang GitHub của repo chính, **click vào nút Fork ở góc trên
bên phải. Một hộp thoại xuất hiện, bạn chỉ việc click Create Fork**.

Từ đó về sau, **chỉ viết code ở trong repo mà bạn đã fork** (cũng
như clone, pull, push...), **chứ không phải ở repo chính**.

Ngoài ra, bạn chỉ được phép sửa các file mà bạn được giao việc.
**KHÔNG được chỉnh sửa các file không thuộc trách nhiệm của mình**.

Khi công việc của bạn hoàn thành, vào repo mà bạn đã fork trên GitHub.
Sẽ có hai nút bấm nằm dưới nút `Code` màu xanh, đó là `Contribute` và
`Sync Fork`.

![Contribute and Sync Fork buttons](images/contribute-or-sync-fork.png)

Click nút `Contribute`, rồi click `Open Pull Request`. Đặt tên
hợp lý cho pull request của bạn, rồi click `Create Pull Request` ở
phía dưới cùng. Các đóng góp của bạn sẽ được người quản lý (admin) nhập
vào trong repo chính. (Thay vì push thẳng lên repo chính, bạn phải fork
nó, chỉnh sửa các phần thuộc trách nhiệm của bạn, rồi tạo pull request
trở lại repo chính. Và chỉ khi admin duyệt pull request của bạn, các
đóng góp của bạn mới được nhập vào trong repo chính).

Khi công việc của bạn chưa hoàn thành, nhưng bạn cần lấy code trong các
file của các thành viên khác về repo đã fork của mình (chẳng hạn: để chạy
thử), vẫn làm theo các bước như trên, nhưng click nút `Sync Fork` thay vì
`Contribute`. Sau
đó [làm theo các bước trong link này](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/working-with-forks/syncing-a-fork).

Cuối cùng và cũng cực kỳ quan trọng, chú ý rằng **các commit messages của
bạn phải tuân thủ các quy tắc của [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).**

## Thiết lập dự án trước khi viết code

1. Clone repo về máy riêng (máy tính cá nhân) của bạn
   (local machine).
2. Đảm bảo local machine đã được cài đặt các phần mềm
   sau:
    - JDK 11 trở lên
    - IntelliJ IDEA
3. Mở thư mục chứa repo bằng IntelliJ IDEA.
4. Trong IntelliJ IDEA, ấn phím `Ctrl` hai lần, gõ:
   `mvn clean package install` rồi bấm Enter.

## Quy trình Phát triển

**Sau khi thiết lập xong theo các bước như trên, bạn
không cần làm lại các bước đó nữa.** Chỉ cần viết code
trong các file mà bạn được phân công.

Nếu muốn chạy thử:

1. Mở file `Main.java` của module CLI hoặc GUI. Có một
   nút bấm Run màu xanh ở bên trái dòng code chứa lệnh
   khai báo class `Main`, hoặc lệnh khai báo hàm `main`
   của class `Main`.

   ![Run or Debug the module](images/run-or-debug.png)

2. Click nút Run màu xanh, rồi chọn `Run Main.main()`
   hoặc `Debug Main.main()` để chạy module đó trực tiếp.

3. Nếu bạn muốn chạy thử CLI với các đối số dòng lệnh
   (command line arguments), bấm nút Run màu xanh, rồi
   chọn `Modify Run Configuration`. Trong mục `Program
   arguments`, nhập vào command line arguments bạn
   muốn, rồi click OK. Sau đó, lặp lại quy trình ở
   bước 2.

   ![Program arguments](images/program-arguments.png)
