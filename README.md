This is an Ecommerce app that uses Clean Architecture & Components

- About

It simply loads Product list from https://fakestoreapi.com/. Provides filter to view products based on category type. Added product list in cart will be always loaded 
from local database. Remote data (from API) and Local data is always synchronized.

Features Present 

1. Load product list from API
2. Add product from product list, product details and save in local database
3. Sync the product added with local database
4. View cart from product list, product details
5. Filter product list based on category
6. View bill in cart
7. Get added product list from local database
8. Modify product quantity once added from anywhere
9. Select payment method before placing order
10. Place order

Future Developments

1. My Orders screen to show placed orders
2. Add Address functionality to deliver the order to the address
3. Add user, can have multiple users

Modular approch followed
It is heavily implemented by following standard clean architecture principle.
Offline capability.
Code understandable, flexible and maintainable.

- Built With 🛠
Kotlin -
First class and official programming language for Android development.

Android Architecture Components - 
Collection of libraries that help you design robust, testable, and maintainable apps.

LiveData - 
Data objects that notify views when the underlying database changes.

ViewModel - 
Stores UI-related data that isn't destroyed on UI changes.

Room - 
SQLite object mapping library.

Dagger Hilt - 
Dependency Injection Framework

Retrofit - 
A type-safe HTTP client for Android and Java.

OkHttp3 - 
HTTP client that's efficient by default: HTTP support allows all requests to the same host to share a socket

Glide - 
image loading framework for Android

Gson - 
used to convert Java Objects into their JSON representation and vice versa.

Chucker -
used to show request response of the API's called inside app

What is clean architecture?
Architecture means the overall design of the project. It's the organization of the code into classes or files or 
components or modules. And it's how all these groups of code relate to each other. The architecture defines where the application performs 
its core functionality and how that functionality interacts with things like the database and the user interface.

Why the cleaner approach?
Separation of code in different layers with assigned responsibilities making it easier for further modification.
High level of abstraction
Loose coupling between the code
Clean code

Layers
Domain - Would execute business logic which is independent of any layer and is just a pure kotlin/java package with no android specific dependency.
Data - Would dispense the required data for the application to the domain layer by implementing interface exposed by the domain.
Presentation / framework - Would include both domain and data layer and is android specific which executes the UI logic.
