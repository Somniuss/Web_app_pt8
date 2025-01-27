<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ATMOSPHERE</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-image: url('${pageContext.request.contextPath}/pictures/background.jpg');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            color: #fff;
            text-align: center;
        }
        h1 {
            color: #fff;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
            margin: 0;
        }
        .sound-item {
            margin: 15px 0;
        }
        audio {
            display: block;
            margin: 10px auto;
        }
        button.download {
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            background-color: #080808;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button.download:hover {
            background-color: #303032;
        }
        .footer {
            text-align: center;
            margin-top: 20px;
            font-size: 0.9em;
            color: #ddd;
        }
    </style>
</head>
<body>
    <h1>ATMOSPHERE</h1>
    <p class="lead">Immerse yourself in atmospheric sounds</p>
    <div class="content">
        <!-- Sound 1 -->
        <div class="sound-item">
            <p>Atmosphere Sound 1</p>
            <audio controls>
                <source src="${pageContext.request.contextPath}/sounds/atmosphere1.mp3" type="audio/mpeg">
                Your browser does not support the audio element.
            </audio>
            <a href="${pageContext.request.contextPath}/sounds/atmosphere1.mp3" download>
                <button class="download">Download</button>
            </a>
        </div>
        <!-- Sound 2 -->
        <div class="sound-item">
            <p>Atmosphere Sound 2</p>
            <audio controls>
                <source src="${pageContext.request.contextPath}/sounds/atmosphere2.mp3" type="audio/mpeg">
                Your browser does not support the audio element.
            </audio>
            <a href="${pageContext.request.contextPath}/sounds/atmosphere2.mp3" download>
                <button class="download">Download</button>
            </a>
        </div>
    </div>
    <div class="footer">
        <p>&copy; 2024 SREBNAJE</p>
    </div>
</body>
</html>
