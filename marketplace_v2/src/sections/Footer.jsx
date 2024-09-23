import { copyrightSign } from "../assets/icons";
import { footerLinks, socialMedia } from "../constants";

const Footer = () => {
  return (
    <footer className='max-container'>
      <div className='flex justify-between items-start gap-10 flex-wrap max-lg:flex-col'>
        <div className='flex flex-col items-start text-white text-[24px]'>
          <a href='/'>
            Marketplace
          </a>
          <p className='mt-4 text-sm leading-6 font-montserrat text-white-400 sm:max-w-sm'>
            Your trusted destination for quality products, delivered with care.
          </p>
          <div className='flex items-center gap-4 mt-6'>
            {socialMedia.map((icon) => (
              <div
                className='flex justify-center items-center w-10 h-10 bg-white rounded-full'
                key={icon.alt}
              >
                <img src={icon.src} alt={icon.alt} width={20} height={20} />
              </div>
            ))}
          </div>
        </div>

        <div className='flex flex-1 justify-between lg:gap-6 gap-12 flex-wrap'>
          {footerLinks.map((section) => (
            <div key={section.title}>
              <h4 className='font-montserrat text-lg leading-normal font-medium mb-4 text-white'>
                {section.title}
              </h4>
              <ul>
                {section.links.map((link) => (
                  <li
                    className='mt-2 font-montserrat text-sm leading-normal text-white-400 hover:text-slate-gray'
                    key={link.name}
                  >
                    <a href={link.link}>{link.name}</a>
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>
      </div>

      <div className='flex justify-between text-white-400 mt-16 max-sm:flex-col max-sm:items-center'>
        <div className='flex flex-1 justify-start items-center gap-2 font-montserrat cursor-pointer text-sm'>
          <img
            src={copyrightSign}
            alt='copyright sign'
            width={16}
            height={16}
            className='rounded-full m-0'
          />
          <p>Copyright. All rights reserved.</p>
        </div>
        <p className='font-montserrat cursor-pointer text-sm mt-2 max-sm:mt-4'>
          Terms & Conditions
        </p>
      </div>
    </footer>
  );
};

export default Footer;
