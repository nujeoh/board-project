<img width="500" src="https://github.com/user-attachments/assets/a8510227-562f-452a-bedc-756d5a92947c">

## プロジェクト概要
> 新しく学習した ORM（Object Relational Mapping）を適用し、 <br> ユーザー関連のロジックや投稿、コメントなどの基本的な CRUD を実装した個人プロジェクトです。
<br>

## 1. 使用技術
 - バックエンド : Java 17, Spring Boot 3.3.4
 - フロントエンド : Thymeleaf, HTML, CSS (BootStrap), JavaScript (jQuery)
 - データベース : MySQL 8
 - ORM : Spring Data JPA(Hibernate)
 - バリデーション : Spring Validation
 - ビルド·ツール : Gradle
 - その他のツール : Lombok
<br>

## 2. 機能概要
 - ユーザー管理 : 新規登録、ログイン、プロフィール修正
 - 投稿管理 : 投稿作成、修正、削除、詳細表示
 - コメント機能 : コメント作成、削除
 - エラーハンドリング : 共通エラーおよび例外処理
<br>

## 3. 主なページおよびURLマッピング

 - 投稿関連

| URLパス                        | HTTP Method | 説明                   |
|-------------------------------|-------------|-----------------------|
| `/board`                      | GET         | 投稿リストページ          |
| `/board/write`                | GET         | 投稿作成ページ           |
| `/board/write`                | POST        | 新しい投稿を登録する       |
| `/board/post/{postId}`        | GET         | 特定の投稿の詳細ページ     |
| `/board/post/{postId}/update` | GET         | 特定の投稿修正ページ       |
| `/board/post/{postId}/update` | POST        | 修正内容を更新する        |
| `/board/post/{postId}/delete`&nbsp;&nbsp;&nbsp;&nbsp; | POST        | 投稿を削除する&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |

 - ユーザー関連

| URLパス                        | HTTP Method | 説明                   |
|-------------------------------|-------------|-----------------------|
| `/user/login`                 | GET         | ログインページ           |
| `/user/login`                 | POST        | ユーザーログイン処理       |
| `/user/logout`                | GET         | ログアウト処理           |
| `/user/join`                  | GET         | 会員登録ページ           |
| `/user/join`                  | POST        | 会員登録処理             |
| `/user/mypage`                | GET         | マイページ              |
| `/user/mypage`                | POST        | マイページ情報の更        |
| `/user/delete/{userId}`&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| POST        | ユーザーアカウントの削除処理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |
<br>

## 4. APIドキュメント
  
 - 投稿関連

| エンドポイント                   | Method      | 説明                   |
|------------------------------|-------------|-----------------------|
| `/board/search`&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| GET&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | 指定された基準で投稿を検索し、<br>検索結果をページング形式で返す&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |

- コメント関連

| エンドポイント                   | Method      | 説明                   |
|------------------------------|-------------|-----------------------|
| `/reply/write`               | POST        | 新しいコメントを登録し、<br>登録内容を返す |
| `/reply/list`                | GET         | コメントリストを取得       |
| `/reply/delete`              | POST        | コメントの削除を処理       |
| `/nested-reply/write`        | POST        | 新しい返信コメントを登録し、<br>登録内容を返す |
| `/nested-reply/delete`&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| POST&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | 返信コメントの削除を処理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |

- ユーザー関連

| エンドポイント                      | Method      | 説明                        |
|---------------------------------|-------------|----------------------------|
| `/api/account/{account}/exists` | GET         | アカウントの存在を確認           |
| `/api/password/check`           | POST&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | ユーザーIDとパスワードの一致を確認 |
<br>

## 5. ディレクトリ構造
  
```main
project
└── src
    └── main
        ├── java
        │   └── com.example.project
        │        ├── post            // ユーザー関連のロジック
        │        │   ├── controller  // コントローラー層（Controller, RestController）
        │        │   ├── db          // データアクセス層（Entity, Repository）
        │        │   ├── model       // RequestDTO, ResponseDTO
        │        │   └── service     // ビジネスロジック層（Service, Converter）
        │        ├── reply           // コメント、返信関連のロジック
        │        │   ├── controller
        │        │   ├── db
        │        │   ├── model
        │        │   └── service
        │        ├── user            // ユーザー関連のロジック
        │        │   ├── controller
        │        │   ├── db
        │        │   ├── model
        │        │   └── service
        │        │
        │        └── exception       // 例外処理関連のロジック
        │            ├── exceptions  // カスタム例外
        │            └── handler     // ExceptionHandler
        │
        └── resources
            ├── static               // 静的リソース
            ├── templates            // Thymeleafテンプレート
            └── application.yml      // 設定ファイル
```
<br>

## 6. データベース設計
 <img width="955" alt="スクリーンショット 2024-11-02 午後11 00 14" src="https://github.com/user-attachments/assets/67bad086-da2e-4019-9978-156bf900e1ff">
<br>

## 7. 画面設計
| **メイン/リストページ**  |  **検索する時**      |
| :-----------------: | :----------------: |
| <img width="450" src="https://github.com/user-attachments/assets/3b54d1f6-fef4-41ae-a661-2e424f568afa"/> | <img width="450" src="https://github.com/user-attachments/assets/378f290f-efc9-47e3-8a2c-e345f61379fb"/>|
| **会員登録ページ**      |  **マイページ**      |
| <img width="450" src="https://github.com/user-attachments/assets/d3ae9656-dbf9-4bd5-bb19-83d85a79ebca"/> | <img width="450" src="https://github.com/user-attachments/assets/6615efd4-0f16-4f5b-bbf3-f0915a80ce8a"/>|
| **ログインページ**      |  **投稿詳細ページ**   |
| <img width="450" src="https://github.com/user-attachments/assets/bffd6dfd-9a6c-4718-9c7f-15f2854ec299"/> | <img width="450" src="https://github.com/user-attachments/assets/03124c64-ff10-4bd2-84c2-55018c3e9ff1"/>|
| **投稿作成ページ**      |  **投稿修正ページ**   |
| <img width="450" src="https://github.com/user-attachments/assets/5c28399c-e683-4a41-b5f2-a76776bbc79d"/> | <img width="450" src="https://github.com/user-attachments/assets/517bbe26-2d7e-4562-a6f1-7b8597f8693c"/>|

<br>

## 8. 追加したい機能 / 改善すべき点
 - 追加したい機能 : ソーシャルログイン、通知システムなど
 - 改善すべき点 : パフォーマンスの向上、セキュリティの強化、サービスのデプロイなど
