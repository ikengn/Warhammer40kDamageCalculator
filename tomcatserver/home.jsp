<!DOCTYPE html> 
 <html>
    <script>
        document.addEventListener("DOMContentLoaded",function set(){
            var homeNav = document.getElementById("homeNav");
            homeNav.classList.add("active");
        })
    </script>
        <head>
            <meta charset="utf-8">
            <title>Home Page</title>
            <link rel="stylesheet" href="style.css">
        </head>
        <body id="homeBody">
            <%@ include file="navBar.html" %>
            <img src="images/warhammer.png" alt="Warhammer 40k Logo" style="margin-top:50px; display: block; margin-left: auto; margin-right: auto; width: 50%;">
            <div>
                <h1 class = "warhammerFont whiteText" style="text-align: center; padding-top: 10px"><u>What is Warhammer?</u></h1>
                <p class = "whiteText" style="text-align: center; font-size: large;">Warhammer is a tabletop battle game which puts players in command of armies of valiant humans, noble elves, savage orcs or a variety of twisted and monstrous creatures. 
                    <br>Players collect forces of miniature plastic models, all with different stats and abilities, and use them to play out clashes on a tabletop battlefield. 
                    Unlike a board game, <br>where players moves are restricted to defined areas, 
                    Warhammer commanders freely manoeuvre <br>their units set distances 
                    using rulers and resolve shooting and hand-to-hand combat by rolling dice.</p>
            </div>
            <div>
                <h1 class="warhammerFont whiteText" style="text-align: center; padding-top: 10px"><u>What is this service for?</u></h1>
                <p class = "whiteText" style="text-align: center; font-size: large;">The purpose of this service is to be able to particpate in simulations to see how your units would perform<br>
                You can select both friendly and enemy units to conduct a simulation as well as provide manual dice rolls or allow for randomization
                <br>Additionally our service provides the ability to view previous damage calculations through the usage of our history page</p>
                <br> 
                <p class = "whiteText" style="text-align: center; font-size: large;">On the next page you will be able to select said units above as well as their desired weapons. Upon doing so you will continue through <br>
                the stages of the typical battle phase in which you will see if you <b>hit your target</b> then next if you will <b>wound your target</b> <br>
                and finally you will then see how much <b>damage</b> is inflicted upon your enemy. With each step of the way indicating a new dice roll that can be randomized <br>
                or it can be manually set by you, the user.</p>
            </div>
            <div id="text-center">
                <button class="button-18" onclick="window.location.href='damageCalculator.jsp'" style="margin-bottom: 50px; margin-top: 50px"> Advance to Damage Calculator </button>
            </div>
        </body>
    </html>