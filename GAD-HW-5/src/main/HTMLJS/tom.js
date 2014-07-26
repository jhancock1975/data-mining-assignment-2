//***********************************************************
// Copyright 2013 Dr. Thomas Fernandez
//       All rights reserved.
//***********************************************************

//"use strict";


var drawMode = "FreeStyle";


function doit1() {
    drawMode = "FreeStyle";
}

function doit2() {
    drawMode = "LineStyle";
}

function drawThreeTriangles() {

    // reset the transform
    g.setTransform(1, 0, 0, 1, 0, 0);

    setRandGradient();
    g.fillRect(0, 0, g.canvas.width, g.canvas.height);

    g.scale(1, 1);
    g.translate(g.canvas.width / 2, g.canvas.height / 2);
    var rC = 255;
    var gC = 128;
    var bC = 64;
    var aC = 1.0;
    g.fillStyle = randColor();



    //shadow
    g.fillStyle = "Black";
    g.shadowOffsetX = 20;
    g.shadowOffsetY = 20;
    g.shadowBlur = 20;
    //g.shadowColor = randColor();



    var unit = g.canvas.width * 0.2;

    var offset = g.canvas.width * 0.25;

    g.shadowColor = randColor();

    setRandGradient();

    drawTriangle((0), -unit / 2,
        (-unit / 2), unit / 2,
        (unit / 2), unit / 2,
        true);

    g.shadowColor = randColor();

    setRandGradient();

    drawTriangle(-offset + (0), -unit / 2,
        -offset + (-unit / 2), unit / 2,
        -offset + (unit / 2), unit / 2,
        true);

    g.shadowColor = randColor();

    setRandGradient();

    drawTriangle(offset + (0), -unit / 2,
        offset + (-unit / 2), unit / 2,
        offset + (unit / 2), unit / 2,
        true);

    g.shadowColor = "transparent";


}

function setRandGradient() {
    var gradient = g.createLinearGradient(Math.random() * g.canvas.width, Math.random() * g.canvas.height, Math.random() * g.canvas.width, Math.random() * g.canvas.height);
    gradient.addColorStop(0.0, randColor());
    gradient.addColorStop(0.5, randColor());
    gradient.addColorStop(1.0, randColor());
    g.fillStyle = gradient;
}

function tColor(r, g, b, a) {
    var result = 'rgba(' + r + ',' + g + ',' + b + ',' + a + ')';
    return result;
}

function randLightColor() {
    var rC = Math.floor(Math.random() * 256);
    var gC = Math.floor(Math.random() * 256);
    var bC = Math.floor(Math.random() * 256);
    while (Math.max(rC, gC, bC) < 200) {
        rC = Math.floor(Math.random() * 256);
        gC = Math.floor(Math.random() * 256);
        bC = Math.floor(Math.random() * 256);
    }
    return tColor(rC, gC, bC, 1.0);
}

function randColor() {
    var rC = Math.floor(Math.random() * 256);
    var gC = Math.floor(Math.random() * 256);
    var bC = Math.floor(Math.random() * 256);
    return tColor(rC, gC, bC, 1.0);
}

function doit3() {
    drawMode = "SprayStyle";
}

function clear() {
    endTimer();
    // reset the transform
    g.setTransform(1, 0, 0, 1, 0, 0);
    //g.restore()
    // erase everything
    g.clearRect(0, 0, g.canvas.width, g.canvas.height);
}

function startTimer() {
    if (!timerRunning) {
        greyOutButton("butStart");
        ungreyButton("butEnd");
        clear();
        drawThreeTriangles();
        var interval = document.getElementById("timerSlider").value;
        //timerID = setInterval("drawRandTriangle()", interval);
        timerRunning = true;
        timerID = setInterval("drawThreeTriangles()", interval);
    }
}

function endTimer() {
    if (timerRunning) {
        greyOutButton("butEnd");
        ungreyButton("butStart");
        clearInterval(timerID);
        timerRunning = false;
    }
}

function drawRandTriangle() {
    g.fillStyle = randColor();
    g.strokeStyle = randColor();
    drawTriangle(Math.random() * g.canvas.width, Math.random() * g.canvas.height,
        Math.random() * g.canvas.width, Math.random() * g.canvas.height,
        Math.random() * g.canvas.width, Math.random() * g.canvas.height,
        true);
}

function drawTriangle(x1, y1, x2, y2, x3, y3, fill) {
    g.beginPath();
    g.moveTo(x1,y1);
    g.lineTo(x2,y2);
    g.lineTo(x3,y3);
    g.closePath();
    if (fill) {
        g.fill();
    }
    else {
        g.stroke();
    }
}

function greyOutButton(butID) {
    document.getElementById(butID).style.backgroundColor = "lightGrey";
    document.getElementById(butID).style.color = "grey";
}

function ungreyButton(butID) {
    document.getElementById(butID).style.backgroundColor = "lightBlue";
    document.getElementById(butID).style.color = "black";
}


function readFile() {
    var txtFile = new XMLHttpRequest();
    txtFile.open("GET", ".//readme.txt", false);
    txtFile.onreadystatechange = function () {
        if (txtFile.readyState === 4) {  // Makes sure the document is ready to parse.
            if (txtFile.status === 200) {  // Makes sure it's found the file.
                allText = txtFile.responseText;
                lines = txtFile.responseText.split("\n"); // Will separate each line into an array
                alert(lines);
                
            }
            else {
                //alert("File not found.");
            }
        }
        else {
            alert("File not ready.");
        }
    }
    txtFile.send(null);
}

function onMouseUpEventHandler(e) {
    switch(drawMode) {
        case "FreeStyle":
            g.restore();
            drawingOn = false;

            e.preventDefault();
            e.stopPropagation();
            e.target.style.cursor = 'auto';
            break;
        case "LineStyle":
            break;
        case "SprayStyle":

            break;
    }
}

function onMouseDownEventHandler(e) {
    switch(drawMode) {
        case "FreeStyle":
            g.save();
            g.setTransform(1, 0, 0, 1, 0, 0);

            e.preventDefault();
            e.stopPropagation();
            e.target.style.cursor = 'crosshair';

            if (e.offsetX) {
                oldX = e.offsetX;
                oldY = e.offsetY;
            }
            else if (e.layerX) {
                oldX = e.layerX;
                oldY = e.layerY;
            }
            //alert("mdown");
            g.strokeStyle = drawColor;
            drawingOn = true;
            //g.strokeStyle = "blue";
            g.lineCap = 'round';
    
            g.lineWidth = document.getElementById("penWidthSlider").value;
            break;
        case "LineStyle":
            break;
        case "SprayStyle":
            //alert("mouse down spraystyle");
            if (e.offsetX) {
                newX = e.offsetX;
                newY = e.offsetY;
            }
            else if (e.layerX) {
                newX = e.layerX;
                newY = e.layerY;
            }
            for (var i = 0; i < 15; i++) {
                g.beginPath();
                newX += (Math.random() * 30) - (Math.random() * 30);
                newY += (Math.random() * 30) - (Math.random() * 30);
                radius = Math.random() * 15;
                g.fillStyle = drawColor;
                g.strokeStyle = drawColor;
                //g.moveTo(newX, newY);
                g.arc(newX, newY, radius, 0, 2 * Math.PI, false);
                g.fill();
            }
            break;
    }

}



function onMouseMoveEventHandler(e) {
    switch(drawMode) {
        case "FreeStyle":
        var mX = 0;
        var my = 0;
        if (drawingOn) {
            if (e.offsetX) {
                mX = e.offsetX;
                mY = e.offsetY;
            }
            else if (e.layerX) {
                mX = e.layerX;
                mY = e.layerY;
            }

            g.beginPath();
            g.moveTo(oldX, oldY);
            g.lineTo(mX, mY);
            //g.closePath();
            g.stroke();
            oldX = mX;
            oldY = mY;
        }
        break;
        case "LineStyle":
            break;
        case "SprayStyle":
            break;
    }
}

function pickCol1() {
    drawColor = document.getElementById('cButton1').style.backgroundColor;
}


function pickCol2() {
    drawColor = document.getElementById('cButton2').style.backgroundColor;
}


function pickCol3() {
    drawColor = document.getElementById('cButton3').style.backgroundColor;
}

function randomizeColorButtons() {
    document.getElementById('cButton1').style.backgroundColor = randLightColor();
    document.getElementById('cButton2').style.backgroundColor = randLightColor();
    document.getElementById('cButton3').style.backgroundColor = randLightColor();
}

//making a var outside of all function makes it global
var g;

function onloadEventHandler() {
    drawingOn = false;
    //alert("loading");
    // make global g (canvas context)
    var can = document.getElementById('canvas123');
    g = can.getContext('2d');
    g.canvas.width = window.innerWidth - 15;
    g.canvas.height = window.innerHeight * .9;
    g.save();

    can.addEventListener("mousemove", onMouseMoveEventHandler, false);
    can.addEventListener("mouseup", onMouseUpEventHandler, false);
    can.addEventListener("mousedown", onMouseDownEventHandler, false);



    //change button text
    document.querySelector('#but1').textContent = 'FreeStyle';
    document.querySelector('#but2').textContent = 'LineStyle';
    document.querySelector('#but3').textContent = 'SprayStyle';


    //document.getElementById('but1').style.backgroundColor = randLightColor();
    //document.getElementById('but2').style.backgroundColor = randLightColor();
    //document.getElementById('but3').style.backgroundColor = randLightColor();
    //document.getElementById('butClear').style.backgroundColor = randLightColor();

    document.getElementById('cButton1').style.backgroundColor = randLightColor();
    document.getElementById('cButton2').style.backgroundColor = randLightColor();
    document.getElementById('cButton3').style.backgroundColor = randLightColor();

    // make other global variables
    PI = 3.141592653589793;
    twoPI = PI * 2.0;
    timerRunning = false;

    // setup button event handlers
    document.querySelector('#but1').onclick = doit1;
    document.querySelector('#but2').onclick = doit2;
    document.querySelector('#but3').onclick = doit3;
    document.querySelector('#butClear').onclick = clear;

    document.getElementById('cButton1').onclick = pickCol1;
    document.getElementById('cButton2').onclick = pickCol2;
    document.getElementById('cButton3').onclick = pickCol3;
    document.getElementById('RandomColorButton').onclick = randomizeColorButtons;

    drawColor = document.getElementById('cButton1').style.backgroundColor;



    // Check for the various File API support.
    if (window.File && window.FileReader && window.FileList && window.Blob) {
        // Great success! All the File APIs are supported.
        //alert('The File APIs are fully supported in this browser.');
    } else {
        alert('The File APIs are not fully supported in this browser.');
    }

    readFile();
}

//this makes onloadEventHandler the event handler for the "load" event
window.addEventListener("load", onloadEventHandler, false);


