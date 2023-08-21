package LoginEx.com.ex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * このソースコードは、Spring Frameworkを使用してWebアプリケーションを開発する際に、追加のリソースの提供方法を設定するクラスです。
 * AdditionalResourceWebConfigurationクラスは、Springの設定クラスであることを示す@Configurationアノテーションを持っています。
 * Springは、このクラスを読み込み、アプリケーションの設定として使用します。
 * このクラスはWebMvcConfigurerインタフェースを実装しています。WebMvcConfigurerは、Spring MVCアプリケーションの設定をカスタマイズするためのインタフェースです。
 * addResourceHandlers(ResourceHandlerRegistry registry)メソッドは、リソースの処理方法を設定するためのものです。
 * このメソッドは、ResourceHandlerRegistryオブジェクトを引数として受け取ります。
 * registry.addResourceHandler("/images/**").addResourceLocations("file:images/");は、
 * "/images/"というパスに対するリクエストがあった場合に、"file:images/"というディレクトリ内のリソースを返すように設定しています。
 * つまり、"/images/"へのリクエストは、"images/"ディレクトリ内のリソースにマッピングされます。
 * これにより、Spring MVCアプリケーション内で"/images/**"へのリクエストが行われた場合、"images/"ディレクトリ内のリソースが提供されます。
 * 例えば、"/images/example.jpg"へのリクエストは、実際には"images/example.jpg"というファイルを返すことになります。
 * この設定により、特定のパスへのリクエストに対してファイルシステム内のリソースが提供されるようになります
 *
 * **/
@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/root/blog-app/images/**").addResourceLocations("file:images/");
    }
}