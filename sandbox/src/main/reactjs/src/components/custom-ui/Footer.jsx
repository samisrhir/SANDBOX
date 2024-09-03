
const Footer = () => {
  return (
    <footer class="bg-gray-800 text-white py-12">
    <div class="container mx-auto px-4">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <div class="lg:text-left">
                <h3 class="text-primary font-light mb-2">Thank you for choosing us!</h3>
                <h5 class="font-light mb-0">Let's get in touch on any of these platforms.</h5>
            </div>
            <div class="flex justify-center lg:justify-end items-center">
                <a href="https://twitter.com/hpsmaroc" target="_blank" class="btn-icon-only rounded-full btn-secondary"><i class="fab fa-twitter"></i></a>
                <a href="https://www.linkedin.com/company/hps" target="_blank" class="btn-icon-only rounded-full ml-2 btn-secondary"><i class="fab fa-linkedin"></i></a>
                <a href="https://www.youtube.com/channel/UCG8SC8iSUZlsqR9oOyvG7nA" target="_blank" class="btn-icon-only rounded-full ml-2 btn-secondary"><i class="fab fa-youtube"></i></a>
            </div>
        </div>
        <hr class="my-8 border-gray-600" />
        <div class="flex flex-col md:flex-row justify-between items-center">
            <div>&copy; 2024 <a href="https://www.hps-worldwide.com/about-hps" target="_blank" class="text-blue-400">Copyright HPS. All rights reserved.</a></div>
            <div class="md:text-right mt-4 md:mt-0"></div>
        </div>
    </div>
</footer>
  )
}

export default Footer