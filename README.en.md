# WoojuMat (Best Restaurants Around Us)

## Translations
Engligh (this file)  
한국어 ([README.md](README.md))

## Overview
An Android application and backend server developed as a team project in the Mobile Programming course.

This application allows users to register and review restaurants in their vicinity.
Restaurants can be described using hashtags such as "#GoodForConversation #Lively #KoreanFood," which characterize their atmosphere.
The search feature supports unified searches by name, address, or hashtags.
User can rate restaurants on taste, price, service, and cleanliness, each on a scale of 1 to 5.

## How to Run the Application
You can find the APK file from "Release" tab on the right, or navigate to "Artifact" and download recent build.
Due to Freenom (a free domain name provider) not functional at this point, backend server is not running.
You may need to replace value of `SERVER_URL` from [RemoteService.java](app/src/main/java/cf/spacetaste/app/network/RemoteService.java).

## Build Instructions
The application can be built using Gradle.

However, due to Kakao SDK's build key verification, unauthorized users cannot build and test the Android application. If you wish to build it, please replace the Kakao API key with yours.

For the backend, copy the `docker-compose.yaml` file from the `backend` directory to an appropriate location and execute it.
Before using the `docker-compose.yaml` file, you must first generate the image using the following command:

- `:app:build` → Builds the application
- `:backend:bootBuildImage` → Creates a Docker image using GraalVM Native

## Key External Libraries

### Common
- Lombok: Used for managing data classes

### Client-Side
- Android SDK: Targets SDK 32
- AndroidX: Various helper libraries
- Retrofit2: Used for REST API calls
- Glide: Used for loading and displaying images from the server
- Kakao SDK: Implements Kakao login and map functionalities

### Server-Side
- Spring Boot: Used for web service development
- Spring Boot AOT: Used for native compilation
- MyBatis: Used for managing SQL statements
- MariaDB JDBC Connector: Used for integrating with MariaDB server
- OkHttp: Used for calling Kakao REST API to verify tokens
- Auth0-JWT: Used for issuing and verifying proprietary access tokens

## License
This project is distributed under the MIT License.
For more details, please refer to `LICENSE.txt`.
