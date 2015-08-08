/**
 * Rotate an tile image give its id.
 */
function rotate(id) {
	var direction = document.getElementById("dir_" + id);
	// increment one in direction
	var directionIndex = (parseInt(direction.value) +1) %4;
	setRotation(id, directionIndex);
}

function setRotation(id, directionIndex) {
	var img = document.getElementById("img_" + id);
	var direction = document.getElementById("dir_" + id);
	// direction vs rotation ==> [0,270], [1,0], [2,90], [3,180]
	// 1 is 0 degrees as 1 means card is pointing to player 0
	var degrees = ((directionIndex+3)%4) * 90;
	img.setAttribute("class", "rotate"+degrees);
	direction.value = directionIndex;
}
