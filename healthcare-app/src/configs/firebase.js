import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getFirestore } from "firebase/firestore";
import { getStorage } from "firebase/storage";
import { getAuth, GoogleAuthProvider, signInWithPopup } from "firebase/auth";


const firebaseConfig = {

  apiKey: "AIzaSyDJMskt3fe-x6LgyCdGEiZ8yvKIP6QmH4k",
  authDomain: "healthy-a110f.firebaseapp.com",
  projectId: "healthy-a110f",
  storageBucket: "healthy-a110f.firebasestorage.app",
  messagingSenderId: "846495658823",
  appId: "1:846495658823:web:20935812430d476f139262",
  measurementId: "G-584FRD0GVC"
};

const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const auth = getAuth(app);
const googleProvider = new GoogleAuthProvider();

export { auth, googleProvider, signInWithPopup };

export const db = getFirestore(app);
export const storage = getStorage(app);
