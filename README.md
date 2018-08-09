# tumbldown

A tumblr search application.

How to start the tumbldown application
---

1. Set an environment variable named `TUMBLR_API_KEY` to your [Tumblr API key aka OAuth Consumer Key](https://www.tumblr.com/docs/en/api/v2#auth). If you don't have one yet, you can start with this sample key: `"fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4"`
1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/tumbldown-0.1.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your application's health, enter url `http://localhost:8081/healthcheck`
