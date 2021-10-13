$(document).ready( function () {
    alert("Hello World!");

    // При наведение мыши
    $('#image1').on('mouseover', function () {
            $('#image1').fadeOut(400)
        }
    )

    // Управляем прозрачностью картинки
    $('#image1').on('click', function () {
            $('#image1').fadeOut(400)
        }
    )

    // Поменяли размер картинки
    // $('#image1').on('click', function () {
    //         $('#image1').width('100px')
    //     }
    // )
}
)