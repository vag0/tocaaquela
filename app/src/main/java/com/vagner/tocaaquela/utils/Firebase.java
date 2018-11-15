package com.vagner.tocaaquela.utils;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;


public class Firebase {

    private static DatabaseReference referenceFirebase;

    private static FirebaseAuth firebaseAuthAutenticacao;

    private static StorageReference storage;

    public static DatabaseReference getFirebase(){

        if(referenceFirebase == null){
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }

        return referenceFirebase;

    }



    public static FirebaseAuth getFirebaseAuthAutenticacao(){

        if(firebaseAuthAutenticacao == null){
            firebaseAuthAutenticacao = FirebaseAuth.getInstance();

        }
        return firebaseAuthAutenticacao;
    }

    public static StorageReference getFirebaseStorage(){

        if(storage == null){

            storage = FirebaseStorage.getInstance().getReference();
        }

        return storage;

    }




}
