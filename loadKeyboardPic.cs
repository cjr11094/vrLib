using UnityEngine;
using System.Collections;

public class loadKeyboardPic : MonoBehaviour {
	
	Texture2D keyboard;
	
	string[] files;
	string pathPreFix;
	public Renderer rend;
	public int index = 0;
	
	// Use this for initialization
	void Start () {
		
		rend = GetComponent <Renderer>();
		
		string path = "/Users/cjr/Documents/vrlib/keyboard/";
		
		pathPreFix = @"file://";
		
		files = System.IO.Directory.GetFiles(path,"*.jpg");
		
		// gameObj = GameObject.FindGameObjectsWithTag("Pics");
		
		Debug.Log ("We're here");
		
		//		LoadImages ();
		
		StartCoroutine (LoadImages ());
		
	}
	
	// Update is called once per frame
	void Update () {
		// make sure that the page is updated
		rend.material.mainTexture = keyboard;
		
		// this will page forward in the text
		if (Input.GetKeyDown ("p")) {
			// in the case where you try to page forward, but we're on the last page
			if (index == files.Length - 1) {
				index = 0;
			} else {
				index++;
			}
		}
		
		// this will page backward in the text
		if (Input.GetKeyDown ("o")) {
			// in the case where you try to page back, but we're on the first page
			if (index == 0) {
				index = files.Length - 1;
			} else {
				index--;
			}
		}
	}
	
	private IEnumerator LoadImages(){
		
	//	keyboard = new Texture2D();
		
		int counter = 0;
		foreach (string tstring in files) {
			Debug.Log(tstring);
			string pathTemp = pathPreFix + tstring;
			WWW www = new WWW(pathTemp);
			yield return www;
			Texture2D texTmp = new Texture2D(1024, 1024, TextureFormat.DXT1, false);
			www.LoadImageIntoTexture(texTmp);
			
			keyboard = texTmp;
			counter++;
		}
		//	GetComponent(MeshRenderer).GetComponent(Material).GetComponent(Texture) = pages[0];
	}
	
}
