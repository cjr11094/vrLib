using UnityEngine;
using System.Collections;

public class UnbreakableCollider : MonoBehaviour {
	public Transform DotPrefab;
	Vector3 lastDotPosition;
	bool lastPointExists;
	
	
	void Start()
	{
//		DotPrefab=Instantiate (DotPrefab, new Vector3 (0, 0, 0), Quaternion.identity);
		lastPointExists = false;
	}
	void Update()
	{
		if (Input.GetMouseButton(0))
		{
			Debug.Log("Left mouse button clicked");
			Ray mouseRay = Camera.main.ScreenPointToRay(Input.mousePosition);
			Vector3 newDotPosition = mouseRay.origin - mouseRay.direction / mouseRay.direction.y * mouseRay.origin.y;
			if (newDotPosition != lastDotPosition)
			{
				MakeADot(newDotPosition);
			}
		}
	}
	void MakeADot(Vector3 newDotPosition)
	{
		Debug.Log ("we called MakeADot");
		Transform dot =(Transform) Instantiate(DotPrefab, newDotPosition, Quaternion.identity); //use random identity to make dots looks more different
		if (lastPointExists)
		{
			GameObject colliderKeeper = new GameObject("collider");
			BoxCollider bc = colliderKeeper.AddComponent<BoxCollider>();
			colliderKeeper.transform.position = Vector3.Lerp(newDotPosition, lastDotPosition, 0.5f);
			colliderKeeper.transform.LookAt(newDotPosition);
			bc.size = new Vector3(0.1f, 0.1f, Vector3.Distance(newDotPosition, lastDotPosition));
		}
		lastDotPosition = newDotPosition;
		lastPointExists = true;
	}
}