function unfollow(id) {
    var unfollowing = {
        id: id
    };

    var httpDeleteFollowing = new XMLHttpRequest();

    httpDeleteFollowing.onreadystatechange = function () {
        if (this.readyState != 4) {
            return;
        }

        response = JSON.parse(this.responseText);
        if (response.goodMsg) {
            var tr = document.querySelector("#account-" + id);
            tr.remove();
            
            var spanFollowingAmount = document.querySelector("#followingAmount");
            var amount = spanFollowingAmount.textContent;
            spanFollowingAmount.textContent = amount == 0 ? 0 : amount - 1;
        }
    }

    httpDeleteFollowing.open("DELETE", contextRoot + "unfollow");
    httpDeleteFollowing.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpDeleteFollowing.send(JSON.stringify(unfollowing));
}