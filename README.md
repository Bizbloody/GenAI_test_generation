# <p align="center"> 🚀 GenAI Test Generation
<p align="center"> <img src="https://img.shields.io/badge/Java-17-orange" /> <img src="https://img.shields.io/badge/Spring_Boot-3.2.2-brightgreen" /> <img src="https://img.shields.io/badge/Azure-OpenAI-blue" /> <img src="https://img.shields.io/badge/PostgreSQL-pgvector-blueviolet" /> <img src="https://img.shields.io/badge/Status-Active-success" /> </p> <p align="center"> <b>Generate unit tests automatically using AI + RAG + Azure</b> </p>

### 🧠  Overview

GenAI Test Generation is a web application that leverages Retrieval-Augmented Generation (RAG) to generate unit tests from source code using AI models hosted on Azure.

👉 The system enriches generation with custom documentation stored as embeddings in a vector database.

### 🎯 Features

✨ Upload technical documentation (PDF)\
🧠 Automatic embedding generation (Azure OpenAI)\
🗄️ Vector storage using PostgreSQL + pgvector\
🔍 Semantic search (Top-K retrieval)\
💻 Upload source code\
⚡ AI-powered test generation\
🧩 Modular multi-agent architecture

### 🖼️ Demo
📄 Upload documentation ( function deactivated for general users )

💻 Generate tests

### ⚙️ Tech Stack
Backend: Spring Boot\
AI: Azure OpenAI (Foundry)\
RAG: LangChain4j\
Database: PostgreSQL + pgvector\
Frontend: HTML / JavaScript

### 🔐 Configuration

All secrets are handled via environment variables:

#### Azure

    AZURE_FOUNDRY_CHAT_ENDPOINT=
    AZURE_FOUNDRY_CHAT_KEY=
    AZURE_FOUNDRY_CHAT_MODEL=

    AZURE_FOUNDRY_EMBED_ENDPOINT=
    AZURE_FOUNDRY_EMBED_KEY=
    AZURE_FOUNDRY_EMBED_MODEL=
#### Database

    SPRING_DATASOURCE_URL=
    SPRING_DATASOURCE_USERNAME=
    SPRING_DATASOURCE_PASSWORD=

#### File Upload
    SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE=10MB
    SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE=10MB

### 🗄️ Database Schema

    CREATE TABLE documents (
        id SERIAL PRIMARY KEY,
        content TEXT,
        embedding VECTOR( <vector length for your embedding model> )
    );

### ▶️ Run the App

run src\main\java\GenAi\GenAiApplication.java

Open:

http://localhost:8080

### 📌 Key Highlights
🔥 Full RAG pipeline implementation\
⚡ Custom vector search with pgvector\
☁️ Azure OpenAI integration\
🧠 Embeddings (3072 dimensions)\
🧩 Clean modular architecture (agents)

### 👨‍💻 Author

Christophe Martinez

