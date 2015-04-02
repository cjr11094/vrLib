#pragma strict


//public GameObject player;

function Start () {
//	player = this.GameObject;
	
}

function Update () {
	if (Input.GetKeyDown ("t")) {
		transform.position.y = 1;	
	}
}