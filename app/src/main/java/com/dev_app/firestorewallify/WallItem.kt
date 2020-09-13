package com.dev_app.firestorewallify


data class WallItem(
    var title: String = "Title",
    //..Use any of the image url in fireStore as default to avoid app crash..
    var image: String = "https://firebasestorage.googleapis.com/v0/b/firestore-wallify.appspot.com/o/wallpapers%2Ffood%2Fpexels-photo-1095550.jpeg?alt=media&token=efcfdf75-f1b3-43f0-9c37-794531807fa4",
    var ref: String = " "
)