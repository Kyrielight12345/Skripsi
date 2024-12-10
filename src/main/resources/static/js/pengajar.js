$(document).ready(function () {
  var role = document.getElementById("role").getAttribute("data-role");
  console.log(role);
  $("#tabel-pengajar").DataTable({
    ajax: {
      method: "GET",
      url: "api/pengajar",
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
      { data: "kelas.name" },
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
                    class="btn btn-warning"
                     onclick="navigateUpdate(${data.id})"
                >
                  Update
                </button>
                <!-- Button trigger modal delete -->
                <button
                  type="button"
                  class="btn btn-danger"
                  onclick="deletePengajar(${data.id})"
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
                  onclick="navigateAndFetch(${data.id})"
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

$("#form-pengajar").on("submit", (event) => {
  event.preventDefault();

  let pengajar = {
    name: $("#create-name").val(),
    alamat: $("#create-alamat").val(),
    tempat_lahir: $("#create-tempat-lahir").val(),
    tanggal_lahir: $("#create-tanggal-lahir").val(),
    tanggal_bergabung: $("#create-tanggal-bergabung").val(),
    jenisKelamin: $("#create-jenis").val(),
    kelas: {
      id: $("#create-kelas").val(),
    },
    username: $("#create-email").val(),
    password: "tpq123",
    role: $("#create-role").val(),
  };
  console.log(pengajar);

  $.ajax({
    method: "POST",
    url: "/api/pengajar/create",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(pengajar),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data Pengajar berhasil disimpan!",
        showConfirmButton: false,
        timer: 2000,
      });
      window.location.href = window.location.origin + "/pengajar";
    },
    error: (err) => {
      Swal.fire({
        position: "center",
        icon: "error",
        title: "Email Sudah Ada!!",
        showConfirmButton: false,
        timer: 2000,
      });
      console.log(err);
    },
  });
});
function navigateAndFetch(id) {
  window.location.href = `/pengajar/detail?id=${id}`;
}
function navigateUpdate(id) {
  window.location.href = `/pengajar/update?id=${id}`;
}

function getById(id) {
  console.log("Fetching details for ID:", id);

  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/pengajar/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      console.log(data);

      var ttl = data.tempat_lahir + "/" + data.tanggal_lahir;
      $("#nama-pengajar").text(data.name);
      $("#kelas-pengajar").text(data.kelas.name);
      $("#kelas-pengajar").text(data.kelas.name);
      $("#alamat-pengajar").text(data.alamat);
      $("#ttl-pengajar").text(ttl);
      $("#gender-pengajar").text(data.jenisKelamin);
      $("#masuk-pengajar").text(data.tanggal_bergabung);

      $("#edit-name").val(data.name);
      $("#edit-alamat").val(data.alamat);
      $("#edit-tempat-lahir").val(data.tempat_lahir);
      $("#edit-tanggal-lahir").val(data.tanggal_lahir);
      $("#edit-tanggal-bergabung").val(data.tanggal_bergabung);
      $("#edit-jenis").val(data.jenisKelamin);
      $("#edit-kelas").val(data.kelas.id);
      $("#edit-id").val(data.id);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

$("#edit-santri").on("submit", (event) => {
  event.preventDefault();
  let valueId = $("#edit-id").val();
  let santriData = {
    name: $("#edit-name").val(),
    alamat: $("#edit-alamat").val(),
    tempat_lahir: $("#edit-tempat-lahir").val(),
    tanggal_lahir: $("#edit-tanggal-lahir").val(),
    tanggal_bergabung: $("#edit-tanggal-bergabung").val(),
    jenisKelamin: $("#edit-jenis").val(),
    kelas: {
      id: $("#edit-kelas").val(),
    },
  };
  console.log(santriData);

  $.ajax({
    method: "PUT",
    url: window.location.origin + "/api/pengajar/" + valueId,
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(santriData),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data Pengajar berhasil diupdate!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/pengajar";
      });
    },
    error: (err) => {
      console.log("salah");
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

function deletePengajar(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/pengajar/" + id,
    success: function (data) {
      Swal.fire({
        title: "Apakah Yakin Ingin Menghapus " + data.name + " ?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Ya",
      }).then((result) => {
        if (result.isConfirmed) {
          $.ajax({
            method: "Delete",
            url: window.location.origin + "/api/pengajar/" + id,
            beforeSend: addCSRFToken(),
            success: function (res) {
              console.log(res);
              Swal.fire(" Data Berhasil Dihapus.");
              $("#tabel-pengajar").DataTable().ajax.reload();
            },
            error: function (err) {
              console.log(err);
            },
          });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          swal.fire({
            title: "batal",
            text: res.name + " Tidak terhapus",
            icon: "error",
          });
        }
      });
    },
  });
}
