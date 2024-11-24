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
});

function navigateAndFetch(id) {
  window.location.href = `/progress/detail?id=${id}`;
}

function getById(id) {
  console.log("Fetching details for ID:", id);

  $.ajax({
    method: "GET",
    url: "http://localhost:1006/api/progress/santri/" + id,
    success: function (data) {
      console.log("Fetched data:", data);

      $("#nama").text(data[0].santri.name);
      $("#jilid").text(data[0].tanggal_progress);
      $("#pengajar").text(data[0].pengajar.name);

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
                  <button type="button" class="btn btn-warning" onclick="navigateAndFetch(${data.santri.id})">
                    Update
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
  const santriId = urlParams.get("id");

  if (santriId) {
    getById(santriId);
  } else {
    console.error("ID Santri tidak ditemukan di URL");
  }
});
