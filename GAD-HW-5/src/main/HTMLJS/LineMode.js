/**
 * LineMode
 * Function Object for encapsulating line mode functionality
 * 
 * We decided to take a functional approach because 
 * it cuts down on spelling-error bugs.
 * 
 * if you misspell the name of a variable or a function that is 
 * a method of the variable, an error informing you of this will
 * be logged to the JavaScriptConsole.
 */
LineMode  = function() {
	var lineModeStartX = null;
	setLineModeStartX = function(x){
		lineModeStartX = x;
	};
	getLineModeStartX =  function(){
		return lineModeStartX;
	};
	var lineModeStartY = null;
	setLineModeStartY = function(y){
		lineModeStartY=y;
	};
	getLineModeStartY = function(){
		return lineModeStartY;
	};
	var drawingLine =  false;
	setInLineMode = function(mode){
		drawingLine = mode;
	};
	inLineMode =  function(){
		return drawingLine;
	}
	return this;
}