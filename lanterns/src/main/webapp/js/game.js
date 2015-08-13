/**
 * Rotate an tile image clockwise.
 * @param id	The id of the image to rotate.
 */
function rotate(id) {
	var direction = document.getElementById("dir_" + id);
	// increment one in direction
	var directionIndex = (parseInt(direction.value) +1) %4;
	setRotation(id, directionIndex);
}

/**
 * Set rotation of tile image.
 * @param id	The id of the image to set rotation.
 * @param directionIndex	direction {0,1,2,3} will determine the rotation in degrees: [0,270], [1,0], [2,90], [3,180].
 */
function setRotation(id, directionIndex) {
	var img = document.getElementById("img_" + id);
	var direction = document.getElementById("dir_" + id);
	// direction vs rotation ==> [0,270], [1,0], [2,90], [3,180]
	// 1 is 0 degrees as 1 means card is pointing to player 0
	var degrees = ((directionIndex+3)%4) * 90;
	img.setAttribute("class", "rotate"+degrees);
	direction.value = directionIndex;
}

function prepareAction(selectedAction, owner) {
	var myDiv = selectedAction;
	var actions = ["exchange", "dedication", "place"];
	
	actions.forEach(function(action) {
		var currentDiv = document.getElementById(action + "_div_" + owner);
		if (myDiv === action) {
			currentDiv.style["pointer-events"] = "auto";
			currentDiv.style["opacity"] = 1.0;
		} else {
			currentDiv.style["pointer-events"] = "none";
			currentDiv.style["opacity"] = 0.5;
		}
	});
}

function exchange(owner) {
	// TODO - validate
	takeAction("exchange");
}

function dedication(owner) {
	// TODO - validate
	takeAction("dedication");
}

function place(owner) {
	// TODO - validate
	takeAction("place");
}

function takeAction(selectedAction) {
	var gameForm = document.getElementById("gameForm");
	gameForm.playerAction.value = selectedAction;
	gameForm.submit();
}

// setInterval(function(){ alert("Hello"); }, 3000);

function aiPlay() {
	var nextAction = document.getElementById("nextAction");
	if (nextAction.value !== "endGame") {
		setInterval(function() {takeAction(nextAction.value);}, 1000);
	}
}

function activatePlayer() {
	var currentPlayer = document.getElementById("currentPlayer");
	var currentPlayerAIType = document.getElementById("currentPlayerAIType");
	
	if (currentPlayerAIType.value === "HUMAN") {
		var playerDiv = document.getElementById("player_" + currentPlayer.value);
		playerDiv.style["pointer-events"] = "auto";
		// FIXME - set correct action after load 
		prepareAction("exchange", currentPlayer.value);
	} else {
		aiPlay();
	}
}


function onPageLoad() {
	activatePlayer();
}

// last thing, on page load
window.onload=onPageLoad;

