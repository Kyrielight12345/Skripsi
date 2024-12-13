var role = document.getElementById("role").getAttribute("data-role");
console.log(role);

$(document).ready(function () {
  $("#tabel-progress").DataTable({
    ajax: {
      method: "GET",
      url: "api/progress/santri",
      dataSrc: function (data) {
        console.log("Received Data: ", data);

        return Object.keys(data).map((key) => data[key][0]);
      },
    },
    columns: [
      {
        data: null,
        render: (data, type, row, meta) => {
          return meta.row + 1;
        },
      },
      {
        data: "santri.name",
      },
      {
        data: "santri.jilid.name",
      },
      {
        data: null,
        render: (data) => {
          return `
              <div class="d-flex gap-3 justify-content-center">
                <button
                  type="button"
                  class="btn btn-primary"
                  onclick="navigateAndFetch(${data.santri.id})"
                >
                  Detail
                </button>
              </div>
            `;
        },
      },
    ],
  });
  fetch(window.location.origin + "/api/santri")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok " + response.statusText);
      }
      return response.json();
    })
    .then((data) => {
      console.log("Santri Data: ", data);
      const santriDropdown = document.getElementById("create-santri");
      santriDropdown.innerHTML = "";

      data.forEach((santri) => {
        const option = document.createElement("option");
        option.value = santri.id;
        option.textContent = santri.name;
        santriDropdown.appendChild(option);
      });
    })
    .catch((error) => {
      console.error("Error fetching santri data: ", error);
    });
});

function navigateAndFetch(id) {
  window.location.href = `/progress/detail?id=${id}`;
}
function navigateUpdate(id) {
  const url = `/progress/update?id=${id}`;
  window.location.href = `/progress/update?id=${id}`;

  window.history.pushState({ id }, "", url);
}

function getById(id) {
  console.log("Fetching details for ID:", id);

  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/progress/santri/" + id,
    success: function (data) {
      console.log("Fetched data:", data);

      $("#nama").text(data[0].santri.name);
      $("#jilid").text(data[0].santri.jilid.name);
      // $("#pengajar").text(data[0].pengajar.name);

      $("#tabel-detail-progress").DataTable({
        data: data,
        columns: [
          {
            data: null,
            render: (data, type, row, meta) => {
              return meta.row + 1;
            },
          },
          {
            data: "tanggal_progress",
          },
          {
            data: "halaman",
          },
          {
            data: "keterangan",
          },
          {
            data: null,
            render: (data) => {
              if (role == "[ROLE_PENGAJAR]") {
                return `
                <div class="d-flex gap-3 justify-content-center">
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
                  onclick="deleteNilai(${data.id})"
                >
                  Delete
                </button>
                </div>
              `;
              } else {
                return "-";
              }
            },
          },
        ],
      });
    },
    error: function (xhr, status, error) {
      console.error("Error fetching data:", error);
      alert("Failed to load data.");
    },
  });
}

document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get("id");

  if (id) {
    const path = window.location.pathname;

    if (path.includes("/progress/detail")) {
      getById(id);
    } else if (path.includes("/progress/update")) {
      beforeUpdate(id);
    } else {
      console.error("Tidak ada tindakan yang sesuai untuk path:", path);
    }
  } else {
    console.error("ID tidak ditemukan di URL");
  }
});

$("#form-progress").on("submit", (event) => {
  event.preventDefault();

  let progress = {
    tanggal_progress: $("#create-tanggal").val(),
    keterangan: $("#create-keterangan").val(),
    halaman: $("#create-halaman").val(),
    santri: {
      id: $("#create-santri").val(),
    },
    pengajar: {
      id: 1,
    },
  };

  console.log("Data yang akan dikirim:", progress);

  $.ajax({
    method: "POST",
    url: window.location.origin + "/api/progress/create",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(progress),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Progress berhasil disimpan!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/progress";
      });
    },
    error: (err) => {
      Swal.fire({
        position: "center",
        icon: "error",
        title: "Terjadi kesalahan saat menyimpan progress!",
        showConfirmButton: false,
        timer: 2000,
      });
      console.error("Error:", err);
    },
  });
});

function deleteNilai(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/progress/" + id,
    success: function (data) {
      Swal.fire({
        title: "Apakah Yakin Ingin Menghapus ?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Ya",
      }).then((result) => {
        if (result.isConfirmed) {
          $.ajax({
            method: "Delete",
            url: window.location.origin + "/api/progress/" + id,
            beforeSend: addCSRFToken(),
            success: function (res) {
              Swal.fire("Berhasil! " + " berhasil Dihapus.");
              window.location.reload();
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

$("#edit-progress").on("submit", (event) => {
  event.preventDefault();
  let id = $("#create-progress-id").val();
  let progress = {
    tanggal_progress: $("#create-tanggal").val(),
    keterangan: $("#create-keterangan").val(),
    halaman: $("#create-halaman").val(),
    santri: {
      id: $("#create-santri").val(),
    },
    pengajar: {
      id: 1,
    },
  };

  console.log(progress);
  $.ajax({
    method: "PUT",
    url: window.location.origin + "/api/progress/" + id,
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(progress),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data Nilai berhasil diupdate!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/progress";
      });
    },
    error: (err) => {
      Swal.fire({
        position: "center",
        icon: "error",
        title: "Terjadi kesalahan!",
        showConfirmButton: true,
      });
    },
  });
});

function beforeUpdate(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/progress/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      console.log(data);
      $("#create-tanggal").val(data.tanggal_progress);
      $("#create-keterangan").val(data.keterangan);
      $("#create-halaman").val(data.halaman);
      $("#create-santri").val(data.santri.id);
    },
    error: function (err) {
      console.log(err);
    },
  });
}
