using UnityEngine;
using System.Collections;

public class loadImages : MonoBehaviour {

	Texture2D[] pages;

	string[] files;
	string pathPreFix;
	public Renderer rend;
	public int index = 0;

	// Use this for initialization
	void Start () {

		rend = GetComponent <Renderer>();

		string path = "/Users/cjr/Documents/vrlib/doleTexts/";

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
		rend.material.mainTexture = pages [index];

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

		pages = new Texture2D[files.Length];

		int counter = 0;
		foreach (string tstring in files) {
			Debug.Log(tstring);
			string pathTemp = pathPreFix + tstring;
			WWW www = new WWW(pathTemp);
			yield return www;
			Texture2D texTmp = new Texture2D(1024, 1024, TextureFormat.DXT1, false);
			www.LoadImageIntoTexture(texTmp);

			pages[counter] = texTmp;
			counter++;
		}
	//	GetComponent(MeshRenderer).GetComponent(Material).GetComponent(Texture) = pages[0];
	}

}
