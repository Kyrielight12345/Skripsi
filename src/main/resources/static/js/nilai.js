$(document).ready(function () {
  var role = document.getElementById("role").getAttribute("data-role");
  console.log(role);
  $("#tabel-nilai").DataTable({
    ajax: {
      method: "GET",
      url: "api/nilai/santri",
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
  const url = `/nilai/detail?id=${id}`;
  window.location.href = `/nilai/detail?id=${id}`;
  window.history.pushState({ id }, "", url);
  getById(id);
}

function navigateUpdate(id) {
  const url = `/nilai/update?id=${id}`;
  window.location.href = `/nilai/update?id=${id}`;

  window.history.pushState({ id }, "", url);
  getNilai(id);
}

document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get("id");

  if (id) {
    const path = window.location.pathname;

    if (path.includes("/nilai/detail")) {
      getById(id);
    } else if (path.includes("/nilai/update")) {
      beforeUpdate(id);
    } else {
      console.error("Tidak ada tindakan yang sesuai untuk path:", path);
    }
  } else {
    console.error("ID tidak ditemukan di URL");
  }
});

function getById(id) {
  console.log("Fetching details for ID:", id);

  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/nilai/santri/" + id,
    success: function (data) {
      console.log("Fetched data:", data);

      $("#nama").text(data[0].santri.name);
      $("#jilid").text(data[0].santri.jilid.name);

      $("#tabel-detail-nilai").DataTable({
        data: data,
        columns: [
          {
            data: null,
            render: (data, type, row, meta) => {
              return meta.row + 1;
            },
          },
          {
            data: "al_harakat",
          },
          {
            data: "makhraj",
          },
          {
            data: "imla",
          },
          {
            data: "an_namroh",
          },
          {
            data: "al_lafadz",
          },
          {
            data: "tajwid",
          },
          {
            data: "gharib",
          },
          {
            data: "al_mad",
          },
          {
            data: "tanggal_ujian",
          },
          {
            data: null,
            render: (data) => {
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

function beforeUpdate(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/nilai/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      console.log(data);
      $("#create-al_harakat").val(data.al_harakat);
      $("#create-makhraj").val(data.makhraj);
      $("#create-imla").val(data.imla);
      $("#create-an_namroh").val(data.an_namroh);
      $("#create-al_lafadz").val(data.al_lafadz);
      $("#create-tajwid").val(data.tajwid);
      $("#create-gharib").val(data.gharib);
      $("#create-al_mad").val(data.al_mad);
      $("#create-tanggal-Ujian").val(data.tanggal_ujian);
      $("#create-santri").val(data.santri.id);
      $("#create-nilai-id").val(data.id);
    },
    error: function (err) {
      console.log(err);
    },
  });
}

$("#form-nilai").on("submit", (event) => {
  event.preventDefault();

  let nilai = {
    al_harakat: $("#create-al_harakat").val(),
    makhraj: $("#create-makhraj").val(),
    imla: $("#create-imla").val(),
    an_namroh: $("#create-an_namroh").val(),
    al_lafadz: $("#create-al_lafadz").val(),
    tajwid: $("#create-tajwid").val(),
    gharib: $("#create-gharib").val(),
    al_mad: $("#create-al_mad").val(),
    tanggal_ujian: $("#create-tanggal-Ujian").val(),
    santri: {
      id: $("#create-santri").val(),
    },
  };

  console.log("Data yang akan dikirim:", nilai);

  $.ajax({
    method: "POST",
    url: window.location.origin + "/api/nilai/create",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(nilai),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data nilai berhasil disimpan!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/nilai";
      });
    },
    error: (err) => {
      Swal.fire({
        position: "center",
        icon: "error",
        title: "Terjadi kesalahan saat menyimpan data nilai!",
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
    url: window.location.origin + "/api/nilai/" + id,
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
            url: window.location.origin + "/api/nilai/" + id,
            beforeSend: addCSRFToken(),
            success: function (res) {
              Swal.fire("Berhasil! " + res.name + " berhasil Dihapus.");
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

$("#edit-nilai").on("submit", (event) => {
  event.preventDefault();
  let id = $("#create-nilai-id").val();
  let nilaiData = {
    al_harakat: $("#create-al_harakat").val(),
    makhraj: $("#create-makhraj").val(),
    imla: $("#create-imla").val(),
    an_namroh: $("#create-an_namroh").val(),
    al_lafadz: $("#create-al_lafadz").val(),
    tajwid: $("#create-tajwid").val(),
    gharib: $("#create-gharib").val(),
    al_mad: $("#create-al_mad").val(),
    tanggal_ujian: $("#create-tanggal-Ujian").val(),
    santri: {
      id: $("#create-santri").val(),
    },
  };

  console.log(nilaiData);
  $.ajax({
    method: "PUT",
    url: window.location.origin + "/api/nilai/" + id,
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(nilaiData),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data Nilai berhasil diupdate!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/nilai";
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
