#pragma strict

var book: Texture2D[];

var one = 2;

var tracker = 0;

var texSize = 256;
var lines = 20;

//var mous: MouseLook[];
 
var mouseLookScript : MouseLook;
 //mouseLookScript = Camera.main.GetComponent(MouseLook);
 //mouseLookScript.enabled = true;
   
function Start () {

//	mous = Camera.main.GetComponent(MouseLook);
//	mous[0].enabled = false;
//	mous[1].enabled = false;

	mouseLookScript = Camera.main.GetComponent(MouseLook);
	mouseLookScript.enabled = false;
	var tex = book[tracker];
	for( var i = 0; i < lines; i++ ){
		TextureDraw.Line(tex, Random.Range(0, texSize), Random.Range(0, texSize), Random.Range(0, texSize), Random.Range(0, texSize),
		Color(Random.Range(0.25, 1.0), Random.Range(0.25, 1.0), Random.Range(0.25, 1.0)) );
	}
	tex.Apply();
	GetComponent.<Renderer>().material.mainTexture = tex;
}

function Update () {


}

function OnMouseDown(){
	
	if(tracker == 0){
		tracker++;
		GetComponent.<MeshRenderer>().material.mainTexture = book[tracker];
	} else {
		tracker--;
		GetComponent.<MeshRenderer>().material.mainTexture = book[tracker];
	}


}