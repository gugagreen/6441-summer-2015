/**
 * Rotate an tile image give its id.
 */
function rotate(id) {
	var img = document.getElementById("img_" + id);
	var direction = document.getElementById("dir_" + id);
	if (direction.value == 0) {
		img.setAttribute("class", "rotate90");
		direction.value = 1;
	} else if (direction.value == 1) {
		img.setAttribute("class", "rotate180");
		direction.value = 2;
	} else if (direction.value == 2) {
		img.setAttribute("class", "rotate270");
		direction.value = 3;
	} else if (direction.value == 3) {
		img.setAttribute("class", "rotate0");
		direction.value = 0;
	}
}