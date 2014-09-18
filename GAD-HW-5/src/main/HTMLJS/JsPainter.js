//***********************************************************
//based on sample code from 
//http://faculty.eng.fau.edu/thomas/classes/graphical-application-development/sample-code/
//Copyright 2013 Dr. Thomas Fernandez
//All rights reserved.
//***********************************************************


var drawMode = "FreeStyle";

/**
 * handles the freestyle button
 */
function doit1() {
	drawingOn = false;
	drawMode = "FreeStyle";
}

/**
 * handles the line style button
 */
function doit2() {
	drawMode = "LineStyle";
	lineMode.setClickCount(lineMode.ZERO_CLICK());
}

/**
 * produces  color css string
 * @param r
 * @param g
 * @param b
 * @param a
 * @returns {String}
 */
function tColor(r, g, b, a) {
	var result = 'rgba(' + r + ',' + g + ',' + b + ',' + a + ')';
	return result;
}

/**
 * produces random light color
 * @returns
 */
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

/**
 * produces random color
 * @returns
 */
function randColor() {
	var rC = Math.floor(Math.random() * 256);
	var gC = Math.floor(Math.random() * 256);
	var bC = Math.floor(Math.random() * 256);
	return tColor(rC, gC, bC, 1.0);
}

/**
 * handles spray style button
 */
function doit3() {
	drawingOn = false;
	drawMode = "SprayStyle";
}

function clear() {
	// reset the transform
	g.setTransform(1, 0, 0, 1, 0, 0);
	// g.restore()
	// erase everything
	g.clearRect(0, 0, g.canvas.width, g.canvas.height);
	lineMode.setClickCount(lineMode.ZERO_CLICK());
}

/**
 * Code refactored to avoid duplication
 * 
 * @param g - canvas context
 * @param e - mouse event
 */
function handleGraphicsObjAndEventObj(g, e) {
	g.save();
	g.setTransform(1, 0, 0, 1, 0, 0);
	e.preventDefault();
	e.stopPropagation();
	e.target.style.cursor = 'crosshair';
	g.strokeStyle = tColor( document.getElementById("rSlider").value,
			 document.getElementById("gSlider").value,
			 document.getElementById("bSlider").value,
			 document.getElementById("aSlider").value
			);
	drawingOn = true;
	g.lineCap = 'round';
	g.lineWidth = document.getElementById("penWidthSlider").value;
}

var lineMode = LineMode();

/**
 * code copied from sample code
 * draws a triangle with points
 * passed in, last parameter is color of triangle
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 * @param x3
 * @param y3
 * @param fill
 */
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

/**
 * I implemented these constants as functions
 * that way the script will crash if I misspell the
 * name of the constant.
 * @returns {Number}
 */
function MAX_TRIANGLE_RANDOMIZING(){
	return 20;
}
function MAX_NUM_TRIANGLES() {
	return 5;
}
function drawRandomTriangles(e){
	numTriangles = Math.random() * MAX_NUM_TRIANGLES(); // draw up to 10 triangles 
	for (var i = 0; i < numTriangles; i++){
		drawTriangle(e.offsetX + MAX_TRIANGLE_RANDOMIZING()*Math.random(),
				e.offsetY + MAX_TRIANGLE_RANDOMIZING()*Math.random(),
				e.offsetX + MAX_TRIANGLE_RANDOMIZING()*Math.random(),
				e.offsetY + MAX_TRIANGLE_RANDOMIZING()*Math.random(),
				e.offsetX + MAX_TRIANGLE_RANDOMIZING()*Math.random(),
				e.offsetY + MAX_TRIANGLE_RANDOMIZING()*Math.random(),
				false);
	}
}
/**
 * draws something based on the mode
 * 
 * for line mode, we count clicks modulo three
 * don't draw anything unless its the second mouse down
 * 
 * for spraystyle, draw random triangles
 *  
 * 
 * @param e
 */
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
		switch(lineMode.getClickCount()){
		case lineMode.ZERO_CLICK():
			break;
		case lineMode.FIRST_CLICK():
			break;
		case lineMode.SECOND_CLICK():
			drawLine(g, lineMode.getLineModeStartX(), lineMode.getLineModeStartY(), e.offsetX, e.offsetY);
			lineMode.setClickCount(lineMode.FIRST_CLICK());
			lineMode.setLineModeStartX(e.offsetX);
			lineMode.setLineModeStartY(e.offsetY);
			break;
		}
		break;
	case "SprayStyle":
		drawingOn  = false;
		drawRandomTriangles(e);
		break;
	}
}


/**
 * draws things on mouse down event
 *
 * for linestyle for the first click we remember where clicked
 * otherwise, we increment the number of clicks modulo three
 * 
 * for spraystyle, we just set mode to drawing on
 * @param e
 */
function onMouseDownEventHandler(e) {
	handleGraphicsObjAndEventObj(g, e);
	switch (drawMode) {
	case "FreeStyle":
		oldX = e.offsetX;
		oldY = e.offsetY;
		break;
	case "LineStyle":
		switch (lineMode.getClickCount()){
		case lineMode.ZERO_CLICK():
			lineMode.setLineModeStartX(e.offsetX);
			lineMode.setLineModeStartY(e.offsetY);
			lineMode.setClickCount(lineMode.FIRST_CLICK());
			break;
		case lineMode.FIRST_CLICK():
			lineMode.setClickCount(lineMode.SECOND_CLICK());
			break;
		case lineMode.SECOND_CLICK():
			break;
		}
		break;
	case "SprayStyle":
		drawingOn = true;
		break;
	}

}
/**
 * code refactored to avoid duplication
 * 
 * @param g - graphics context
 * @param oldX - start point x value
 * @param oldY - start point y value
 * @param mX - end point x value
 * @param mY - end point y value
 */
function drawLine(g, oldX, oldY, mX, mY){
	g.beginPath();
	g.moveTo(oldX, oldY);
	g.lineTo(mX, mY);
	g.stroke();
}

/**
 * draws things during mouse motion over canvas
 * 
 * for free style, draws short line to get curvy line effect
 * 
 * for lineStyle - does nothing
 * 
 * for spraystyle draws a trail of random triangles
 * 
 * @param e
 */
function onMouseMoveEventHandler(e) {
	switch (drawMode) {
	case "FreeStyle":
		var mX = 0;
		var my = 0;
		if (drawingOn) {
			mX = e.offsetX;
			mY = e.offsetY;
			drawLine(g, oldX, oldY, mX, mY);
			oldX = mX;
			oldY = mY;
		}
		break;
	case "LineStyle":
		break;
	case "SprayStyle":
		if (drawingOn){
			drawRandomTriangles(e);
		}
		break;
	}
}





//making a var outside of all function makes it global
var g;

function setupCanvasAndGetContext(canvasName){
	var can = document.getElementById(canvasName);
	var g = can.getContext('2d');
	g.canvas.width = window.innerWidth - 15;
	g.canvas.height = window.innerHeight * .9;
	g.save();

	can.addEventListener("mousemove", onMouseMoveEventHandler, false);
	can.addEventListener("mouseup", onMouseUpEventHandler, false);
	can.addEventListener("mousedown", onMouseDownEventHandler, false);

	return g;
}
function onloadEventHandler() {
	drawingOn = false;

	g = setupCanvasAndGetContext('canvas123');

	// change button text
	document.querySelector('#but1').textContent = 'FreeStyle';
	document.querySelector('#but2').textContent = 'LineStyle';
	document.querySelector('#but3').textContent = 'SprayStyle';



	// make other global variables
	PI = 3.141592653589793;
	twoPI = PI * 2.0;
	timerRunning = false;

	// setup button event handlers
	document.querySelector('#but1').onclick = doit1;
	document.querySelector('#but2').onclick = doit2;
	document.querySelector('#but3').onclick = doit3;
	document.querySelector('#butClear').onclick = clear;





	// Check for the various File API support.
	if (window.File && window.FileReader && window.FileList && window.Blob) {
		// Great success! All the File APIs are supported.
		// alert('The File APIs are fully supported in this browser.');
	} else {
		alert('The File APIs are not fully supported in this browser.');
	}

}

//this makes onloadEventHandler the event handler for the "load" event
window.addEventListener("load", onloadEventHandler, false);
