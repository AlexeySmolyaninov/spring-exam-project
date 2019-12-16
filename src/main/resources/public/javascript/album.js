var myAccount;
window.onload = function () {
    var httpMyDetails = new XMLHttpRequest();
    httpMyDetails.open("GET", contextRoot + "myDetails");
    httpMyDetails.send();

    httpMyDetails.onreadystatechange = function () {
        if (this.readyState != 4 || this.status != 200) {
            return;
        }
        myAccount = JSON.parse(this.responseText);
    };

};

function deletePic(id) {
    var picture = {
        id: id
    }

    var httpDeletePicture = new XMLHttpRequest();
    httpDeletePicture.onreadystatechange = function () {

        if (this.readyState != 4) {
            return;
        }

        

        if (this.responseText == "") {
            var body = document.querySelector("body");

            var divAlert = document.createElement("div");
            divAlert.setAttribute("class", "alert alert-danger alert-dismissible");

            divAlert.setAttribute("id", "pic-alert");
            divAlert.textContent = "You can't delete your profile picture!";

            var button = document.createElement("button");
            button.setAttribute("type", "button");
            button.setAttribute("class", "close");
            button.setAttribute("data-dismiss", "alert");
            button.textContent = "X";

            divAlert.appendChild(button);
            body.appendChild(divAlert);
        } else {
            var cardPicture = document.querySelector("#pic-" + id);
            cardPicture.remove();
        }
    };

    var urlDeletePicture = contextRoot + "accounts/" + myAccount.profileName + "/pictures"
    httpDeletePicture.open("DELETE", urlDeletePicture);
    httpDeletePicture.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpDeletePicture.send(JSON.stringify(picture));
}

function setProfilePic(id) {
    var profilePic = {
        id: id
    }
    var urlSetProfilePicture = contextRoot + "accounts/" + myAccount.profileName + "/setprofilepicture"
    httpSetProfilePicture = new XMLHttpRequest();

    httpSetProfilePicture.onreadystatechange = function () {
        if (this.readyState != 4) {
            return;
        }

        response = JSON.parse(this.responseText);
        createNotification(response);
    };

    httpSetProfilePicture.open("POST", urlSetProfilePicture);
    httpSetProfilePicture.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpSetProfilePicture.send(JSON.stringify(profilePic));
}

function createNotification(notificationObj) {
    var body = document.querySelector("body");

    var divAlert = document.createElement("div");
    if (notificationObj.goodMsg) {
        divAlert.setAttribute("class", "alert alert-success alert-dismissible");
    } else {
        divAlert.setAttribute("class", "alert alert-danger alert-dismissible");
    }

    divAlert.setAttribute("id", "pic-alert");
    divAlert.textContent = notificationObj.msg;

    var button = document.createElement("button");
    button.setAttribute("type", "button");
    button.setAttribute("class", "close");
    button.setAttribute("data-dismiss", "alert");
    button.textContent = "X";

    divAlert.appendChild(button);
    body.appendChild(divAlert);
}

function likePic(picid) {
    var httpLikePicture = new XMLHttpRequest();
    httpLikePicture.onreadystatechange = function () {
        if (this.readyState != 4) {
            return;
        }

        response = JSON.parse(this.responseText);

        if (response.goodMsg) {
            var spanLikeCounter = document.querySelector("#pic-" + picid + " span.pic-like span");
            spanLikeCounter.textContent = Number.parseInt(spanLikeCounter.textContent) + 1;
        }

        if (!response.goodMsg) {
            var spanLikeCounter = document.querySelector("#pic-" + picid + " span.pic-like span");
            var likeCounter = Number.parseInt(spanLikeCounter.textContent);
            if (likeCounter > 0) {
                spanLikeCounter.textContent = likeCounter - 1;
            }
        }
    }

    httpLikePicture.open("POST", "/pictures/" + picid + "/like");
    httpLikePicture.send();
    ;
}

