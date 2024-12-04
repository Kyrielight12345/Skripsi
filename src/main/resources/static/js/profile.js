$(document).ready(function () {
  $(".profile-link").click(function (e) {
    e.preventDefault();
    let username = $(this).data("id");

    $("#detail-profile").modal("show");

    $.ajax({
      type: "GET",
      url: "/api/profile/user",
      data: { name: username },
      success: function (data) {
        // detail
        $("#name").text(data.name);
        $("#email").text(data.email);
        $("#phone").text(data.phone);

        // update
        $("#id").val(data.id);
        $("#name1").val(data.name);
        $("#email1").val(data.email);
        $("#phone1").val(data.phone);
      },
      error: function (error) {
        console.error("Error:", error);
      },
    });
  });

  $("#update-profile").click((event) => {
    event.preventDefault();

    let valueId = $("#id").val();
    let valueName = $("#name1").val();
    let valueEmail = $("#email1").val();
    let valuePhone = $("#phone1").val();

    $.ajax({
      method: "PUT",
      url: "api/profile/" + valueId,
      dataType: "JSON",
      contentType: "application/json",
      beforeSend: addCSRFToken(),
      data: JSON.stringify({
        name: valueName,
        email: valueEmail,
        phone: valuePhone,
      }),
      success: (res) => {
        $("#detail-edit").modal("hide");
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Your Profile has been updated...",
          showConfirmButton: false,
          timer: 2000,
        });
        $("#name1").val("");
        $("#email1").val("");
        $("#phone1").val("");
      },
      error: (err) => {
        console.log(err);
      },
    });
  });
});
