//***********************************************************
//Copyright 2013 Dr. Thomas Fernandez
//All rights reserved.
//***********************************************************

//"use strict";

var drawMode = "FreeStyle";

function doit1() {
	drawMode = "FreeStyle";
}

function doit2() {
	drawMode = "LineStyle";
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
	// reset the transform
	g.setTransform(1, 0, 0, 1, 0, 0);
	// g.restore()
	// erase everything
	g.clearRect(0, 0, g.canvas.width, g.canvas.height);
}


function onMouseUpEventHandler(e) {
	switch (drawMode) {
	case "FreeStyle":
		g.restore();
		drawingOn = false;

		e.preventDefault();
		e.stopPropagation();
		e.target.style.cursor = 'auto';
		break;
	case "LineStyle":
		if (lineMode.inLineMode()) {
			console.log('exiting line mode');
			lineMode.setInLineMode(false);
		}
		break;
	case "SprayStyle":

		break;
	}
}
function handleGraphicsObjAndEventObj(g, e) {
	g.save();
	g.setTransform(1, 0, 0, 1, 0, 0);
	e.preventDefault();
	e.stopPropagation();
	e.target.style.cursor = 'crosshair';
	g.strokeStyle = drawColor;
	drawingOn = true;
	g.lineCap = 'round';
	g.lineWidth = document.getElementById("penWidthSlider").value;
}

var lineMode = LineMode();

function onMouseDownEventHandler(e) {
	switch (drawMode) {
	case "FreeStyle":
		handleGraphicsObjAndEventObj(g, e);
		oldX = e.offsetX;
		oldY = e.offsetY;
		break;
	case "LineStyle":
		handleGraphicsObjAndEventObj(g, e);
		lineMode.setInLineMode(true);
		console.log(lineMode.inLineMode());
		lineMode.setLineModeStartX(e.offsetX);
		lineMode.setLineModeStartY(e.offsetY);
		console.log(lineMode.getLineModeStartY());
		break;
	case "SprayStyle":
		newX = e.offsetX;
		newY = e.offsetY;
		for ( var i = 0; i < 15; i++) {
			g.beginPath();
			newX += (Math.random() * 30) - (Math.random() * 30);
			newY += (Math.random() * 30) - (Math.random() * 30);
			radius = Math.random() * 15;
			g.fillStyle = drawColor;
			g.strokeStyle = drawColor;
			g.arc(newX, newY, radius, 0, 2 * Math.PI, false);
			g.fill();
		}
		break;
	}

}

function onMouseMoveEventHandler(e) {
	switch (drawMode) {
	case "FreeStyle":
		var mX = 0;
		var my = 0;
		if (drawingOn) {
			if (e.offsetX) {
				mX = e.offsetX;
				mY = e.offsetY;
			} else if (e.layerX) {
				mX = e.layerX;
				mY = e.layerY;
			}

			g.beginPath();
			g.moveTo(oldX, oldY);
			g.lineTo(mX, mY);
			g.stroke();
			oldX = mX;
			oldY = mY;
		}
		break;
	case "LineStyle":
		if (lineMode.inLineMode()) {
			mX = e.offsetX;
			mY = e.offsetY;
			g.beginPath();
			g.moveTo(lineMode.getLineModeStartX(), lineMode.getLineModeStartY());
			g.lineTo(mX, mY);
			g.stroke();
		}
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

// making a var outside of all function makes it global
var g;

function onloadEventHandler() {
	drawingOn = false;
	
	var can = document.getElementById('canvas123');
	g = can.getContext('2d');
	g.canvas.width = window.innerWidth - 15;
	g.canvas.height = window.innerHeight * .9;
	g.save();

	can.addEventListener("mousemove", onMouseMoveEventHandler, false);
	can.addEventListener("mouseup", onMouseUpEventHandler, false);
	can.addEventListener("mousedown", onMouseDownEventHandler, false);

	// change button text
	document.querySelector('#but1').textContent = 'FreeStyle';
	document.querySelector('#but2').textContent = 'LineStyle';
	document.querySelector('#but3').textContent = 'SprayStyle';

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
		// alert('The File APIs are fully supported in this browser.');
	} else {
		alert('The File APIs are not fully supported in this browser.');
	}

}

// this makes onloadEventHandler the event handler for the "load" event
window.addEventListener("load", onloadEventHandler, false);
