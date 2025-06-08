# 📘 SnapLink API Documentation

SnapLink is a collaborative storytelling platform built with **Spring Boot + JWT Authentication**. Users can create stories, contribute to threaded sentence branches, and explore infinite story paths like Reddit threads.

---

## 🚀 Base URL

```
http://localhost:8080/api
```

---

## 🔐 Authentication

### 🟢 Register

`POST /auth/register`

Registers a new user.

#### 🔸 Request Body

```json
{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "password"
}
```


---

### 🟢 Login

`POST /auth/login`

Logs in a user and returns a JWT token.

#### 🔸 Request Body

```json
{
  "username": "nitesh_rauny",
  "password": "12345678"
}
```


> 📌 Use this token as `Authorization: Bearer <token>` in all protected endpoints.

---

## 📚 Story APIs

### 🟢 Get All Stories

`GET /story/all`

Fetches metadata for all stories.

---

### 🟢 Create a New Story

`POST /story/create`

Creates a story with a root sentence.

#### 🔸 Request Body

```json
{
  "title": "Echoes of the Moon",
  "rootSentence": "The moon whispered secrets to those who dared listen.",
  "description": "A poetic tale of mysticism and lunar wonder."
}
```


---

### 🟢 Get Story By ID

`GET /story/{storyId}`

Returns a single story with root sentence and its children.

---

### 🟢 Add a Child Sentence (Reply)

`POST /story/addChild`

Adds a child node to a sentence in a story.

#### 🔸 Request Body

```json
{
  "storyId": "68378d092cb7855ed4c0f5fb",
  "parentId": "68378d082cb7855ed4c0f5fa",
  "content": "This is a dummy child content for testing."
}
```

---

### 🟢 Get Children of a Node (Lazy Load)

`GET /story/node/{nodeId}/children`

Returns all direct children of a sentence node.


---

## 📆 Authorization Header Format

All authenticated requests must include the following header:

```http
Authorization: Bearer <your_jwt_token>
```

---

## 🧪 Example Flow

1. Register a user → `/auth/register`
2. Login & receive JWT → `/auth/login`
3. Create a story → `/story/create`
4. Add sentence to story → `/story/addChild`
5. Load story → `/story/{storyId}`
6. Expand thread → `/story/node/{nodeId}/children`

---

