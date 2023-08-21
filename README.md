ブログプロジェクト README

皆さん、こんにちは。私のホームページへお越しいただき、大変光栄です。私はリコウチョウと申します。

このページの作者です。私はJavaが大好きで、このブログのページを作成しました。

このページを通して、みなさんにハッピーをお届けできれば幸いです。

このページの様々な機能を実現できるのは、井澤先生のおかげです。先生のご指導に心より感謝申し上げます。


機能特徴

ユーザー認証：アカウントの登録、ログイン、ログアウトが可能です。

記事管理：記事の一覧(index)、投稿、編集、削除ができます。

コメントシステム：ユーザーは記事にコメントを投稿できます。

検索バー：キーワード記事を検索することもできます。

自己紹介画面：あります。

訪問者数のアカウント：できます。

技術スタック

バックエンド：Spring Boot 

使うIDE：eclipse & idea & SpringToolSuite4

フロントエンド：html css javascript 

データベース：PostgreSQL

サーバ：xshell  WinSCP











私のウェブサイトを作成するプロセスは、

まずウェブサイトのデータベース設計を行い、ユースケース図とER図を作成します。

その後、フロントエンドのウェブページの外観を大まかに考え、必要なHTML、JS、CSSファイルを作成します。

フロントエンドの設計が完了したら、バックエンドに移行し、プロパティファイルを設定し、ウェブ開発のファイル構造を構築します。

最初に対応するエンティティを作成し、利用できるDAOインターフェースをいくつか作成し、次にこれらのインターフェースをサービス層で実装します（コントローラーとは別に）。

その後、コントローラーを作成し、Thymeleafを完全に設定し、最後にサーバーにアップロードする際には、AdditionalResourceWebConfigurationがWebMvcConfigurerを実装して静的パスを変更し、ユーザーがアップロードしたファイルを管理しやすくします。

最後はサーバの操作です。

サーバー上での画面のないコード操作の最後の手順は以下の通りです：

PostgreSQL 13のインストール: サーバー上にPostgreSQL 13をインストールします。

具体的な手順は、使用しているサーバーのオペレーティングシステムやディストリビューションによって異なる場合があります。

通常、パッケージマネージャーを使用してインストールすることができます。インストール後、PostgreSQLサービスを開始。

JDK 17のインストール: サーバー上にJDK 17をインストールします。

同様に、オペレーティングシステムやディストリビューションによってインストール方法が異なるかもしれません。

JDKをインストールするときに、JAVA_HOME 環境変数を適切に設定することも必要。

環境変数の設定: 必要な環境変数を設定してください。これには PATH や JAVA_HOME などが含まれます。

これにより、必要なコマンド（PostgreSQLコマンドやJavaコマンドなど）がサーバー上で実行可能になります。

データベースの作成: PostgreSQLにログインし、適切なユーザーとパスワードを使用して新しいデータベースユーザーを作成します。次に、新しいデータベース（例：blog）を作成します。

可視化ツールを使用したファイルのアップロード: WinSCPなどの可視化ツールを使用して、ローカルのjarファイルや必要なファイルをサーバーにアップロードします。アップロードするディレクトリは、適切な権限が設定されているディレクトリであることを確認。

jarファイルの実行: サーバー上でアップロードしたjarファイルを実行します。通常、以下のようなコマンドを使用します。

これらの手順に従って、サーバー上での無画面操作を完了することができます。ただし、サーバーの環境や要件によって手順が異なる場合がありますので、注意して実行してください。









操作の具体的な内容：

JDKの絶対パスを使用して起動する手順：
bash
Copy code
/root/jdk-17.0.8/bin/java -jar LoginEx-0.0.1-SNAPSHOT.jar
/root/jdk-17.0.8/bin/java の部分を、実際のJDKの絶対パスに置き換え、LoginEx-0.0.1-SNAPSHOT.jar の部分を起動するファイル名に変更してください。

バックグラウンドで実行する手順：
bash
Copy code
nohup /root/jdk-17.0.8/bin/java -jar LoginEx-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
このコマンドはバックグラウンドでプロセスを実行し、出力を app.log ファイルにリダイレクトします。プロセスをバックグラウンドで実行するため、コマンドの末尾に & を追加します。プロセスのログを見るには、tail -f app.log を使用します。

フォルダに移動してプロセスを確認する手順：
bash
Copy code
cd /root/blog-app
ps aux | grep java
/root/blog-app の部分を対象のフォルダに置き換えます。ps aux | grep java はバックグラウンドで実行されているJavaプロセスを表示します。

Mavenでプロジェクトをビルドする手順：
bash
Copy code
mvn clean package
このコマンドはMavenを使用してプロジェクトをビルドし、JARファイルなどの成果物を生成します。

PostgreSQLを再起動する手順：
bash
Copy code
sudo systemctl restart postgresql-13
PostgreSQLを再起動します。

PostgreSQLにログインしてデータベースにアクセスする手順：
bash
Copy code
/usr/pgsql-13/bin/psql -U postgres -h localhost
このコマンドは postgres ユーザーでデータベースにアクセスします。 -U オプションでユーザー名を指定し、 -h オプションでホスト名を指定します。

これらの手順を適切に実行することで、サーバー上での操作が行えます。ただし、各手順の前提条件や適用する環境によって異なる場合がありますので、注意して実行してください。





gitのURL：
   https://github.com/li2543868527/blog.git


私のブログプロジェクトをご利用いただき、ありがとうございます！
