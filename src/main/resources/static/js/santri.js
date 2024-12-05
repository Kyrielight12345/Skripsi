var role = document.getElementById("role").getAttribute("data-role");
console.log(role);

$(document).ready(function () {
  $("#tabel-santri").DataTable({
    ajax: {
      method: "GET",
      url: "api/santri",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: (data, row, type, meta) => {
          return meta.row + 1;
        },
      },
      { data: "name" },
      { data: "alamat" },
      {
        data: null,
        render: (data, type, row) => {
          return `${row.tempat_lahir} / ${row.tanggal_lahir}`;
        },
      },
      { data: "jilid.name" },
      { data: "tanggal_bergabung" },
      {
        data: null,
        render: (data) => {
          if (role == "[ROLE_KEPALA]") {
            return `
              <div class="d-flex gap-3 justify-content-center">
                <!-- Button trigger modal detail -->
              <button
                  type="button"
                  class="btn btn-primary"
                  onclick="navigateAndFetch(${data.id})"
                >
                  Detail
                </button>
                <!-- Button trigger modal update -->
                <button
                  type="button"
                  class="btn btn-warning"
                  data-bs-toggle="modal"
                  data-bs-target="#update"
                  onclick="beforeUpdate(${data.id})"
                >
                  Update
                </button>
                <!-- Button trigger modal delete -->
                <button
                  type="button"
                  class="btn btn-danger"
                  onclick="deleteRegion(${data.id})"
                >
                  Delete
                </button>
              </div>
            `;
          } else {
            return `
              <div class="d-flex gap-3 justify-content-center">
                <!-- Button trigger modal detail -->
                <button
                  type="button"
                  class="btn btn-primary"
                  data-bs-toggle="modal"
                  data-bs-target="#detail"
                  onclick="getById(${data.id})"
                >
                  Detail
                </button>
              </div>
            `;
          }
        },
      },
    ],
  });
});
function navigateAndFetch(id) {
  window.location.href = `/santri/detail?id=${id}`;
}

function getById(id) {
  console.log("Fetching details for ID:", id);

  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/santri/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      var ttl = data.tempat_lahir + "/" + data.tanggal_lahir;
      $("#nama").text(data.name);
      $("#jilid").text(data.jilid.name);
      $("#kelas").text(data.jilid.kelas.name);
      $("#alamat").text(data.alamat);
      $("#ttl").text(ttl);
      $("#gender").text(data.jenisKelamin);
      $("#masuk").text(data.tanggal_bergabung);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

$("#form-santri").on("submit", (event) => {
  event.preventDefault();

  let name = $("#create-name").val();
  let username = name;
  let password = username;

  let santriData = {
    name: name,
    alamat: $("#create-alamat").val(),
    tempat_lahir: $("#create-tempat-lahir").val(),
    tanggal_lahir: $("#create-tanggal-lahir").val(),
    tanggal_bergabung: $("#create-tanggal-bergabung").val(),
    jilid: {
      id: $("#create-jilid").val(),
    },
    username: username,
    password: password,
    role: $("#create-role").val(),
  };
  console.log(santriData);

  $.ajax({
    method: "POST",
    url: "/api/santri/create",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(santriData),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data Santri berhasil disimpan!",
        showConfirmButton: false,
        timer: 2000,
      });
      $("#form-santri")[0].reset();
    },
    error: (err) => {
      Swal.fire({
        position: "center",
        icon: "error",
        title: "Terjadi kesalahan!",
        showConfirmButton: false,
        timer: 2000,
      });
      console.log(err);
    },
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const santriId = urlParams.get("id");

  if (santriId) {
    getById(santriId);
  } else {
    console.error("ID Santri tidak ditemukan di URL");
  }
});
