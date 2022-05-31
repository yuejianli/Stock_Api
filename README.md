# StockApi
Stock的后端接口页面。任何情况下，都不建议作为股票投资

# 作为后端接口使用
    location /Stock {
                        try_files $uri $uri/ $uri.html $uri.php$is_args$query_string;
                        root /etc/nginx;
            index page.html page.htm;
        }
                location /StockApi {
                # 把 /stock 路径下的请求转发给真正的后端服务器
                rewrite  ^/(.*)$ /$1 break;
                proxy_pass http://127.0.0.1:8088$request_uri;
                fastcgi_buffers 8 128k;
                send_timeout 60;
                client_max_body_size     100m;
        }

