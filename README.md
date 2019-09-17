## WeDialog

### A simple, support customView dialog library based on DialogFragment. We can use it to show dialog more simply.


### Feature

- **Easier to use**

- **Show dialog on a anchor view**

- **Plenty of properties to setup for custom view**

- **Global configs**

- **Orientation Rotate with data and view-event maintaining**


**e.g:**

you can show a normal dialog just like this.

![](mat/p1.jpg)

```
WeDialog.normal(this)
         .setTitle(getString(R.string.str_notice))
         .setMsg(getString(R.string.str_goog_job))
         .show{
			//ok button clicked
		  }
```

or you can show a dialog with custom view which will anchor another view just like this.


<img src="mat/g1.gif" width="39%" >

```
WeDialog.custom(this)
         .layout(R.layout.dialog_custom0)
         .setWidthRatio(0.3f)
         .setCancelableOutSide(true)
         .anchor(vTvLeftTop)
         .show { _,_,_ ->
          //do sth when view is inflated
		 }
```


### Obtain

add jcenter() firstly:

```
repositories {
     jcenter()
}
```


if you use androidx,please do like this:

```
implementation 'com.cysion:WeDialog:1.0.2.x'
```

else ,do like this:

```
implementation 'com.cysion:WeDialog:1.0.2'
```



