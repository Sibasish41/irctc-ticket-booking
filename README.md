# IRCTC Ticket Booking System

A simple Java console application for booking train tickets inspired by the IRCTC system. It demonstrates user authentication, train search, booking, and cancellation, all using local JSON-based data storage.

---

## Features

- **User Signup & Login** (with password hashing using bcrypt)
- **Train Search** by source and destination
- **Book a Seat** on available trains
- **Fetch User Bookings** (view all your booked tickets)
- **Cancel Booking** by ticket ID
- **Local JSON Storage** for persistence (no database needed)
- **Console-Based Menu System** for user interaction

---

## Tech Stack

- Java 21
- Gradle
- JSON (for local data storage)
- [Jackson](https://github.com/FasterXML/jackson) (for JSON serialization/deserialization)
- [JBCrypt](https://www.mindrot.org/projects/jBCrypt/) (for password hashing)
- [Guava](https://github.com/google/guava)

---

## Project Structure
```
irctc-ticket-booking/
├── app/
│ ├── build.gradle
│ └── src/
│ ├── main/
│ │ └── java/
│ │ └── ticket/
│ │ └── booking/
│ │ ├── App.java
│ │ ├── entitites/
│ │ ├── localDb/
│ │ ├── services/
│ │ └── util/
│ └── test/
├── gradle/
├── .gitattributes
├── .gitignore
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle
```


- **entitites/** – Contains data models like User, Train, Ticket.
- **localDb/** – Local JSON files for users (`users.json`) and trains (`trains.json`).
- **services/** – Core business logic (e.g., `UserBookingService.java`).
- **util/** – Utility classes (e.g., for password hashing).
- **App.java** – Main entry point with interactive menu.

---

## Getting Started

### Prerequisites
- Java 21 installed
- Gradle installed

### Install & Run

1. **Clone the repo:**
```
git clone https://github.com/Sibasish41/irctc-ticket-booking.git
cd irctc-ticket-booking/app
```

2. **Build the project:**
```
gradle build
```

3. **Run the application:**
```
gradle run
```

---

## How to Use

On running, you'll see a menu:
```
1.Sign up
2.Login
3.Fetch Bookings
4.Search Trains
5.Book a Seat
6.Cancel my Booking
7.Exit the App
```

- **Sign up:** Register with username and password
- **Login:** Enter your credentials to proceed
- **Fetch Bookings:** View all booked tickets
- **Search Trains:** Search available trains by source and destination
- **Book a Seat:** After searching, select a train and book
- **Cancel my Booking:** Enter ticket ID to cancel booking

---

## Data Files

- **Trains:** `app/src/main/java/ticket/booking/localDb/trains.json`
- **Users:** `app/src/main/java/ticket/booking/localDb/users.json`

These files will be updated as you interact with the app.

---

## Author

- [Sibasish41](https://github.com/Sibasish41)

---


