## プロジェクト概要
> 新しく学習した ORM（Object Relational Mapping）を適用し、 <br> ユーザー関連のロジックや投稿、コメントなどの基本的な CRUD を実装した個人プロジェクトです。
<br>

## 1.使用技術
 - バックエンド : Java 17, Spring Boot 3.3.4
 - フロントエンド : Thymeleaf, HTML, CSS (BootStrap), JavaScript (jQuery)
 - データベース : MySQL 8
 - ORM : Spring Data JPA(Hibernate)
 - バリデーション : Spring Validation
 - ビルド·ツール : Gradle
 - その他のツール : Lombok
<br>

## 2.機能概要
 - ユーザー管理 : 新規登録、ログイン、プロフィール編集
 - 投稿管理 : 投稿作成、編集、削除、詳細表示
 - コメント機能 : コメント作成、削除
 - エラーハンドリング : 共通エラーおよび例外処理
<br>

## 3.ディレクトリ構造
  
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

## 4.主なページおよび URL マッピング

 - 投稿関連

| URLパス                        | HTTP Method | 説明                   |
|-------------------------------|-------------|-----------------------|
| `/board`                      | GET         | 投稿リストページ          |
| `/board/write`                | GET         | 投稿作成ページ           |
| `/board/write`                | POST        | 新しい投稿を登録する       |
| `/board/post/{postId}`        | GET         | 特定の投稿の詳細ページ     |
| `/board/post/{postId}/update` | GET         | 特定の投稿編集ページ       |
| `/board/post/{postId}/update` | POST        | 編集内容を更新する        |
| `/board/post/{postId}/delete` | POST        | 投稿を削除する           |

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
| `/user/delete/{userId}`       | POST        | ユーザーアカウントの削除処理 |
<br>

## 5.APIドキュメント
  
 - 投稿関連

| エンドポイント                   | Method      | 説明                   |
|-------------------------------|-------------|-----------------------|
| `/board/search`               | GET         | 指定された基準で投稿を検索し、<br>検索結果をページング形式で返す |

- コメント関連

| エンドポイント                   | Method      | 説明                   |
|------------------------------|-------------|-----------------------|
| `/reply/write`               | POST        | 新しいコメントを登録し、<br>登録内容を返す |
| `/reply/list`                | GET         | コメントリストを取得       |
| `/reply/delete`              | POST        | コメントの削除を処理       |
| `/nested-reply/write`        | POST        | 新しい返信コメントを登録し、<br>登録内容を返す |
| `/nested-reply/delete`       | POST        | 返信コメントの削除を処理       |

- ユーザー関連

| エンドポイント                      | Method      | 説明                   |
|---------------------------------|-------------|-----------------------|
| `/api/account/{account}/exists` | GET         | アカウントの存在を確認      |
| `/api/password/check`           | POST        | ユーザーIDとパスワードの一致を確認 |
<br>

## 6.データベース設計
 - 追加予定です。
<br>

## 7.画面設計
 - 追加予定です。
<br>

## 8.追加したい機能 / 改善すべき点
 - 追加したい機能 : ソーシャルログイン、通知システムなど
 - 改善すべき点 : パフォーマンスの向上、セキュリティの強化、サービスのデプロイなど
