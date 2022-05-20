package com.example.medi_sheba.presentation.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.*
import com.example.medi_sheba.R
import com.example.medi_sheba.model.User
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.SecondaryColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.File
import com.google.firebase.auth.FirebaseUser


@Composable
fun UpdateProfileScreen(navController: NavController, auth: FirebaseAuth, userDetails: User) {

    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val authUser = auth.currentUser
    val storageRef: StorageReference = FirebaseStorage.getInstance()
        .reference.child("avatars")

    var imageUri by remember {
        mutableStateOf<Uri>(Uri.parse(userDetails.image))
    }

    if(isLoading) {
        Dialog(
            onDismissRequest = {  },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator(color = PrimaryColor)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            Row {
                Icon(
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "UPDATE PROFILE",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Update your profile information",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(vertical = 15.dp, horizontal = 25.dp)
        ) {

            var name by rememberSaveable { mutableStateOf(userDetails.name) }
            var mobileNumber by rememberSaveable { mutableStateOf(userDetails.mobileNumber) }
            var age by rememberSaveable { mutableStateOf(userDetails.age) }
            var address by rememberSaveable { mutableStateOf(userDetails.address) }
            val gender = remember { mutableStateOf(userDetails.gender) }
            val downloadUrL  = rememberSaveable { mutableStateOf("") }

            Column {

                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                ) {
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri: Uri? ->
                        if (uri != null) {
                            imageUri = uri
                        }
                    }

                    SubcomposeAsyncImage(
                        model = imageUri,
                        contentDescription = "profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                            .clickable {
                                launcher.launch("image/*")
                            },
                    ) {
                        when (painter.state) {
                            is AsyncImagePainter.State.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(35.dp),
                                    color = PrimaryColor
                                )
                            }
                            is AsyncImagePainter.State.Error -> {
                                Image(
                                    painter = painterResource(id = R.drawable.avartar),
                                    contentDescription = "profile image"
                                )
                            }
                            else -> {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Name") },
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it },
                    placeholder = { Text("Mobile") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = age,
                    onValueChange = { age = it },
                    placeholder = { Text("Age") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = address,
                    onValueChange = { address = it },
                    placeholder = { Text("Address") },
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(15.dp))
                displayGenderRadio(gender)
                Spacer(modifier = Modifier.height(15.dp))



                val gradient = Brush.horizontalGradient(listOf(SecondaryColor, PrimaryColor))

                Box(
                    modifier = Modifier
                        .noRippleClickable() {
                            if (imageUri != null) {
                                val ref = storageRef.child("image_${authUser!!.uid}")
                                val uploadTask = ref.putFile(imageUri!!)

                                uploadTask
                                    .continueWithTask { task ->
                                        ref.downloadUrl
                                    }
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val downloadUri = task.result
                                            downloadUrL.value = downloadUri.toString()

                                            isLoading = true
                                            saveDataFirestore(
                                                context,
                                                navController,
                                                isLoading,
                                                name,
                                                userDetails,
                                                mobileNumber,
                                                age,
                                                address,
                                                gender,
                                                downloadUrL,
                                                authUser
                                            )
                                        }
                                    }
                            } else {
                                isLoading = true
                                saveDataFirestore(
                                    context, navController, isLoading, name, userDetails,
                                    mobileNumber, age, address, gender, downloadUrL, authUser!!
                                )
                            }

                        }
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(gradient)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "UPDATE PROFILE",
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}

fun saveDataFirestore(
    context: Context,
    navController: NavController,
    isLoading: Boolean,
    name: String,
    userDetails: User,
    mobileNumber: String,
    age: String,
    address: String,
    gender: MutableState<String>,
    downloadUrL: MutableState<String>,
    authUser: FirebaseUser
) {
    var loading = isLoading

    val db = Firebase.firestore
    val user = User(
        uid = authUser.uid,
        name = name,
        email = userDetails.email,
        userType = "Patient",
        mobileNumber = mobileNumber,
        age = age,
        address = address,
        gender = gender.value,
        image = downloadUrL.value,
//        doctorCategory = userDetails.doctorCategory,
//        doctorDesignation = userDetails.doctorDesignation,
        doctorRating = userDetails.doctorRating
    )

    db
        .collection("users")
        .document(authUser.uid)
        .set(user)
        .addOnSuccessListener {
            loading = false
            navController.popBackStack()
            Toast
                .makeText(
                    context,
                    "Your profile is successfully updated",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        .addOnFailureListener {
            loading = false
            Toast
                .makeText(
                    context,
                    "Something went wrong. Please try again.",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
}
