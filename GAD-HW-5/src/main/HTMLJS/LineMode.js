function LineMode(){
	var lineModeStartX;
	function setLineModeStartX(x){
		lineModeStartX = x;
	}

	var lineModeStartY;
	setLineModeStartY = function(y){
		lineModeStartY=y;
	}
	function getLineModeStartY(){
		return lineModeStartY;
	}
}