import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const FooterBar: React.FC = () => {
  // 현재 선택된 탭을 관리하기 위한 상태
  const [selectedTab, setSelectedTab] = useState<string>('');
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    if (location.pathname.startsWith('/category')) {
      setSelectedTab('category');
    } else if (location.pathname.startsWith('/feed')) {
      setSelectedTab('feed');
    } else if (location.pathname === '/' || location.pathname === '/home') {
      setSelectedTab('home');
    } else if (location.pathname === '/dressroom') {
      setSelectedTab('dressroom');
    } else if (location.pathname === '/mypage') {
      setSelectedTab('mypage');
    } else if (location.pathname.startsWith('/detail/')) {
      setSelectedTab('detail');
    } else {
      setSelectedTab('');
    }
  }, [location.pathname]);

  // 버튼의 스타일을 결정하는 함수
  const getButtonStyle = (tabName: string) => {
    return `flex flex-col font-semibold items-center text-base w-1/4 pt-1 px-1 block ${
      selectedTab === tabName ? 'bg-white text-black' : 'bg-black text-white hover:underline'
    } focus:outline-none`;
  };

  const iconColorStyle = (tabName: string) => {
    return `${selectedTab === tabName ? '#000000' : '#ffffff'}`;
  };

  const handleClick = (tabName: string) => {
    setSelectedTab(tabName);
    navigate(tabName);
  };

  return (
    <footer>
      {selectedTab !== 'detail' && (
        <div className='bg-white h-[55px]'>
          <nav className='flex h-full'>
            <button className={getButtonStyle('category')} onClick={() => handleClick('category')}>
              <svg width='40%' viewBox='0 0 24 24' fill='none' xmlns='http://www.w3.org/2000/svg'>
                <path
                  d='M8 6L21 6.00078M8 12L21 12.0008M8 18L21 18.0007M3 6.5H4V5.5H3V6.5ZM3 12.5H4V11.5H3V12.5ZM3 18.5H4V17.5H3V18.5Z'
                  stroke={iconColorStyle('category')}
                  strokeWidth='2'
                  strokeLinecap='round'
                  strokeLinejoin='round'
                />
              </svg>
              <span className='text-[8px] font-normal relative bottom-[2px]'>카테고리</span>
            </button>
            <button className={getButtonStyle('feed')} onClick={() => handleClick('feed')}>
              <svg
                version='1.0'
                xmlns='http://www.w3.org/2000/svg'
                width='40%'
                viewBox='-50 -50 612 612'
                preserveAspectRatio='xMidYMid meet'
              >
                <g
                  transform='translate(0,512) scale(0.100000,-0.100000)'
                  fill={iconColorStyle('feed')}
                  stroke='none'
                >
                  <path d='M279 5110 c-41 -12 -141 -79 -177 -119 -18 -20 -47 -63 -65 -96 l-32 -60 0 -2070 0 -2070 32 -90 c88 -251 239 -423 466 -530 171 -82 -14 -75 2115 -75 l1903 0 77 26 c240 80 413 257 497 508 l25 75 0 1628 0 1628 -29 60 c-50 106 -142 189 -254 228 -21 7 -185 13 -467 17 l-435 5 -5 315 c-6 337 -7 348 -63 442 -37 62 -92 112 -170 152 l-68 36 -1662 -1 c-914 0 -1674 -4 -1688 -9z m3280 -354 l21 -19 2 -2056 3 -2056 22 -69 c18 -56 45 -118 91 -208 2 -5 -646 -8 -1440 -8 -1597 0 -1505 -3 -1630 66 -161 89 -257 233 -279 418 -6 50 -9 807 -7 1997 3 1604 5 1919 17 1932 12 16 126 17 1410 19 768 2 1480 3 1582 3 169 0 189 -2 208 -19z m1201 -962 c13 -13 15 -188 18 -1512 2 -959 -1 -1524 -7 -1573 -13 -98 -48 -172 -114 -238 -99 -101 -194 -137 -335 -128 -67 4 -96 11 -148 37 -75 37 -156 114 -190 182 -53 104 -51 45 -46 1705 l5 1542 51 3 c28 2 208 2 401 0 297 -2 352 -5 365 -18z' />
                  <path d='M885 4381 c-74 -35 -116 -79 -149 -154 l-26 -58 0 -410 c0 -482 -1 -478 91 -571 88 -90 87 -89 585 -86 l419 3 51 27 c56 30 102 77 136 141 23 41 23 48 26 455 3 468 1 483 -68 571 -24 30 -61 62 -93 78 l-52 28 -430 3 -430 2 -60 -29z m780 -626 l0 -300 -300 0 -300 0 -3 290 c-1 159 0 296 3 303 3 10 68 12 302 10 l298 -3 0 -300z' />
                  <path d='M2393 4280 c-76 -18 -123 -82 -123 -167 0 -62 25 -108 79 -147 l43 -31 352 3 351 3 39 27 c82 56 102 155 50 239 -49 80 -62 82 -439 81 -176 0 -335 -3 -352 -8z' />
                  <path d='M2364 3551 c-65 -29 -89 -72 -89 -156 0 -82 22 -123 85 -155 37 -19 60 -20 380 -20 393 0 396 1 446 86 36 62 36 116 0 179 -49 83 -57 85 -446 85 -305 0 -339 -2 -376 -19z' />
                  <path d='M803 2709 c-78 -38 -115 -146 -77 -222 23 -45 44 -66 86 -88 36 -18 78 -19 1153 -19 l1116 0 44 26 c116 68 114 242 -5 302 l-44 22 -1116 0 c-1110 0 -1115 0 -1157 -21z' />
                  <path d='M850 2011 c-117 -36 -172 -149 -121 -249 23 -46 46 -67 94 -87 32 -13 169 -15 1134 -15 690 0 1111 4 1132 10 81 23 140 126 119 208 -5 22 -23 56 -39 76 -58 69 -7 66 -1213 65 -595 0 -1092 -4 -1106 -8z' />
                  <path d='M839 1291 c-48 -15 -70 -33 -100 -79 -41 -65 -40 -123 4 -188 60 -91 -30 -85 1235 -82 1061 3 1108 4 1141 22 47 25 68 52 85 108 13 42 13 54 0 97 -17 57 -64 105 -121 120 -45 13 -2203 14 -2244 2z' />
                </g>
              </svg>
              <span className='text-[8px] font-normal relative bottom-[2px]'>피드</span>
            </button>
            <button className={getButtonStyle('home')} onClick={() => handleClick('home')}>
              <svg
                version='1.0'
                xmlns='http://www.w3.org/2000/svg'
                width='40%'
                viewBox='-2 -2 20 20'
                preserveAspectRatio='xMidYMid meet'
              >
                <path
                  fill={iconColorStyle('home')}
                  d='M15.45,7L14,5.551V2c0-0.55-0.45-1-1-1h-1c-0.55,0-1,0.45-1,1v0.553L9,0.555C8.727,0.297,8.477,0,8,0S7.273,0.297,7,0.555  L0.55,7C0.238,7.325,0,7.562,0,8c0,0.563,0.432,1,1,1h1v6c0,0.55,0.45,1,1,1h3v-5c0-0.55,0.45-1,1-1h2c0.55,0,1,0.45,1,1v5h3  c0.55,0,1-0.45,1-1V9h1c0.568,0,1-0.437,1-1C16,7.562,15.762,7.325,15.45,7z'
                />
              </svg>
              <span className='text-[8px] font-normal relative bottom-[2px]'>홈</span>
            </button>
            <button
              className={getButtonStyle('dressroom')}
              onClick={() => handleClick('dressroom')}
            >
              <svg
                version='1.0'
                xmlns='http://www.w3.org/2000/svg'
                width='40%'
                viewBox='0 0 512 512'
                preserveAspectRatio='xMidYMid meet'
              >
                <g
                  transform='translate(0,512) scale(0.100000,-0.100000)'
                  fill={iconColorStyle('dressroom')}
                  stroke='none'
                >
                  <path d='M2400 4444 c-277 -73 -477 -325 -482 -608 -1 -75 13 -103 72 -143 64 -44 157 -21 199 48 11 17 22 59 26 93 12 99 41 164 104 227 70 70 141 99 241 99 100 0 171 -29 241 -99 71 -72 101 -144 101 -249 -1 -60 -6 -84 -30 -135 -36 -76 -74 -115 -177 -182 -183 -118 -285 -303 -285 -515 l0 -78 -1087 -628 c-1021 -590 -1092 -633 -1159 -699 -79 -78 -125 -158 -149 -257 -66 -279 102 -558 384 -642 50 -14 253 -16 2164 -16 l2108 0 76 25 c142 48 254 147 317 278 128 268 30 554 -245 717 -52 31 -547 319 -1101 639 l-1008 584 0 62 c0 138 42 208 181 301 117 78 184 149 238 253 53 102 71 178 71 298 0 188 -54 318 -189 453 -98 98 -169 140 -287 170 -92 24 -242 26 -324 4z m1240 -2426 c580 -336 1070 -622 1087 -635 18 -13 44 -43 58 -67 70 -120 24 -276 -99 -332 l-53 -24 -2085 2 -2085 3 -49 30 c-30 19 -60 49 -79 79 -26 43 -30 59 -30 120 0 60 4 79 28 119 15 26 40 56 55 67 76 55 2169 1259 2182 1254 8 -3 489 -280 1070 -616z' />
                </g>
              </svg>
              <span className='text-[8px] font-normal relative bottom-[2px]'>드레스룸</span>
            </button>
            <button className={getButtonStyle('mypage')} onClick={() => handleClick('mypage')}>
              <div className='w-1/2'></div>
              <svg
                version='1.0'
                xmlns='http://www.w3.org/2000/svg'
                width='40%'
                viewBox='0 0 512 512'
              >
                <path
                  fill={iconColorStyle('mypage')}
                  d='M256 298.997333q54.005333 0 112.501333 23.498667t58.496 61.504l0 42.997333-342.005333 0 0-42.997333q0-38.005333 58.496-61.504t112.501333-23.498667zM256 256q-34.997333 0-60-25.002667t-25.002667-60 25.002667-60.501333 60-25.504 60 25.504 25.002667 60.501333-25.002667 60-60 25.002667z'
                />
              </svg>
              <span className='text-[8px] font-normal relative bottom-[2px]'>마이페이지</span>
            </button>
          </nav>
        </div>
      )}
    </footer>
  );
};

export default FooterBar;
