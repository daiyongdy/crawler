package com.crawler.web.touitiao;

/**
 * Created by daiyong on 2017/4/18.
 * email daiyong@coohua.com
 */
public class ASCP {

	public static String script = "function getParam(){\n" +
			"    var asas;\n" +
			"    var cpcp;\n" +
			"    var t = Math.floor((new Date).getTime() / 1e3)\n" +
			"        , e = t.toString(16).toUpperCase()\n" +
			"        , i = md5(t).toString().toUpperCase();\n" +
			"    if (8 != e.length){\n" +
			"        asas = \"479BB4B7254C150\";\n" +
			"        cpcp = \"7E0AC8874BB0985\";\n" +
			"    }else{\n" +
			"        for (var n = i.slice(0, 5), o = i.slice(-5), a = \"\", s = 0; 5 > s; s++){\n" +
			"            a += n[s] + e[s];\n" +
			"        }\n" +
			"        for (var r = \"\", c = 0; 5 > c; c++){\n" +
			"            r += e[c + 3] + o[c];\n" +
			"        }\n" +
			"        asas = \"A1\" + a + e.slice(-3);\n" +
			"        cpcp= e.slice(0, 3) + r + \"E1\";\n" +
			"    }\n" +
			"    return '{\"as\":\"'+asas+'\",\"cp\":\"'+cpcp+'\"}';\n" +
			"}\n" +
			"\n" +
			"!function(e) {\n" +
			"    \"use strict\";\n" +
			"    function t(e, t) {\n" +
			"        var n = (65535 & e) + (65535 & t)\n" +
			"            , r = (e >> 16) + (t >> 16) + (n >> 16);\n" +
			"        return r << 16 | 65535 & n\n" +
			"    }\n" +
			"    function n(e, t) {\n" +
			"        return e << t | e >>> 32 - t\n" +
			"    }\n" +
			"    function r(e, r, o, i, a, u) {\n" +
			"        return t(n(t(t(r, e), t(i, u)), a), o)\n" +
			"    }\n" +
			"    function o(e, t, n, o, i, a, u) {\n" +
			"        return r(t & n | ~t & o, e, t, i, a, u)\n" +
			"    }\n" +
			"    function i(e, t, n, o, i, a, u) {\n" +
			"        return r(t & o | n & ~o, e, t, i, a, u)\n" +
			"    }\n" +
			"    function a(e, t, n, o, i, a, u) {\n" +
			"        return r(t ^ n ^ o, e, t, i, a, u)\n" +
			"    }\n" +
			"    function u(e, t, n, o, i, a, u) {\n" +
			"        return r(n ^ (t | ~o), e, t, i, a, u)\n" +
			"    }\n" +
			"    function s(e, n) {\n" +
			"        e[n >> 5] |= 128 << n % 32,\n" +
			"            e[(n + 64 >>> 9 << 4) + 14] = n;\n" +
			"        var r, s, c, l, f, p = 1732584193, d = -271733879, h = -1732584194, m = 271733878;\n" +
			"        for (r = 0; r < e.length; r += 16)\n" +
			"            s = p,\n" +
			"                c = d,\n" +
			"                l = h,\n" +
			"                f = m,\n" +
			"                p = o(p, d, h, m, e[r], 7, -680876936),\n" +
			"                m = o(m, p, d, h, e[r + 1], 12, -389564586),\n" +
			"                h = o(h, m, p, d, e[r + 2], 17, 606105819),\n" +
			"                d = o(d, h, m, p, e[r + 3], 22, -1044525330),\n" +
			"                p = o(p, d, h, m, e[r + 4], 7, -176418897),\n" +
			"                m = o(m, p, d, h, e[r + 5], 12, 1200080426),\n" +
			"                h = o(h, m, p, d, e[r + 6], 17, -1473231341),\n" +
			"                d = o(d, h, m, p, e[r + 7], 22, -45705983),\n" +
			"                p = o(p, d, h, m, e[r + 8], 7, 1770035416),\n" +
			"                m = o(m, p, d, h, e[r + 9], 12, -1958414417),\n" +
			"                h = o(h, m, p, d, e[r + 10], 17, -42063),\n" +
			"                d = o(d, h, m, p, e[r + 11], 22, -1990404162),\n" +
			"                p = o(p, d, h, m, e[r + 12], 7, 1804603682),\n" +
			"                m = o(m, p, d, h, e[r + 13], 12, -40341101),\n" +
			"                h = o(h, m, p, d, e[r + 14], 17, -1502002290),\n" +
			"                d = o(d, h, m, p, e[r + 15], 22, 1236535329),\n" +
			"                p = i(p, d, h, m, e[r + 1], 5, -165796510),\n" +
			"                m = i(m, p, d, h, e[r + 6], 9, -1069501632),\n" +
			"                h = i(h, m, p, d, e[r + 11], 14, 643717713),\n" +
			"                d = i(d, h, m, p, e[r], 20, -373897302),\n" +
			"                p = i(p, d, h, m, e[r + 5], 5, -701558691),\n" +
			"                m = i(m, p, d, h, e[r + 10], 9, 38016083),\n" +
			"                h = i(h, m, p, d, e[r + 15], 14, -660478335),\n" +
			"                d = i(d, h, m, p, e[r + 4], 20, -405537848),\n" +
			"                p = i(p, d, h, m, e[r + 9], 5, 568446438),\n" +
			"                m = i(m, p, d, h, e[r + 14], 9, -1019803690),\n" +
			"                h = i(h, m, p, d, e[r + 3], 14, -187363961),\n" +
			"                d = i(d, h, m, p, e[r + 8], 20, 1163531501),\n" +
			"                p = i(p, d, h, m, e[r + 13], 5, -1444681467),\n" +
			"                m = i(m, p, d, h, e[r + 2], 9, -51403784),\n" +
			"                h = i(h, m, p, d, e[r + 7], 14, 1735328473),\n" +
			"                d = i(d, h, m, p, e[r + 12], 20, -1926607734),\n" +
			"                p = a(p, d, h, m, e[r + 5], 4, -378558),\n" +
			"                m = a(m, p, d, h, e[r + 8], 11, -2022574463),\n" +
			"                h = a(h, m, p, d, e[r + 11], 16, 1839030562),\n" +
			"                d = a(d, h, m, p, e[r + 14], 23, -35309556),\n" +
			"                p = a(p, d, h, m, e[r + 1], 4, -1530992060),\n" +
			"                m = a(m, p, d, h, e[r + 4], 11, 1272893353),\n" +
			"                h = a(h, m, p, d, e[r + 7], 16, -155497632),\n" +
			"                d = a(d, h, m, p, e[r + 10], 23, -1094730640),\n" +
			"                p = a(p, d, h, m, e[r + 13], 4, 681279174),\n" +
			"                m = a(m, p, d, h, e[r], 11, -358537222),\n" +
			"                h = a(h, m, p, d, e[r + 3], 16, -722521979),\n" +
			"                d = a(d, h, m, p, e[r + 6], 23, 76029189),\n" +
			"                p = a(p, d, h, m, e[r + 9], 4, -640364487),\n" +
			"                m = a(m, p, d, h, e[r + 12], 11, -421815835),\n" +
			"                h = a(h, m, p, d, e[r + 15], 16, 530742520),\n" +
			"                d = a(d, h, m, p, e[r + 2], 23, -995338651),\n" +
			"                p = u(p, d, h, m, e[r], 6, -198630844),\n" +
			"                m = u(m, p, d, h, e[r + 7], 10, 1126891415),\n" +
			"                h = u(h, m, p, d, e[r + 14], 15, -1416354905),\n" +
			"                d = u(d, h, m, p, e[r + 5], 21, -57434055),\n" +
			"                p = u(p, d, h, m, e[r + 12], 6, 1700485571),\n" +
			"                m = u(m, p, d, h, e[r + 3], 10, -1894986606),\n" +
			"                h = u(h, m, p, d, e[r + 10], 15, -1051523),\n" +
			"                d = u(d, h, m, p, e[r + 1], 21, -2054922799),\n" +
			"                p = u(p, d, h, m, e[r + 8], 6, 1873313359),\n" +
			"                m = u(m, p, d, h, e[r + 15], 10, -30611744),\n" +
			"                h = u(h, m, p, d, e[r + 6], 15, -1560198380),\n" +
			"                d = u(d, h, m, p, e[r + 13], 21, 1309151649),\n" +
			"                p = u(p, d, h, m, e[r + 4], 6, -145523070),\n" +
			"                m = u(m, p, d, h, e[r + 11], 10, -1120210379),\n" +
			"                h = u(h, m, p, d, e[r + 2], 15, 718787259),\n" +
			"                d = u(d, h, m, p, e[r + 9], 21, -343485551),\n" +
			"                p = t(p, s),\n" +
			"                d = t(d, c),\n" +
			"                h = t(h, l),\n" +
			"                m = t(m, f);\n" +
			"        return [p, d, h, m]\n" +
			"    }\n" +
			"    function c(e) {\n" +
			"        var t, n = \"\";\n" +
			"        for (t = 0; t < 32 * e.length; t += 8)\n" +
			"            n += String.fromCharCode(e[t >> 5] >>> t % 32 & 255);\n" +
			"        return n\n" +
			"    }\n" +
			"    function l(e) {\n" +
			"        var t, n = [];\n" +
			"        for (n[(e.length >> 2) - 1] = void 0,\n" +
			"                 t = 0; t < n.length; t += 1)\n" +
			"            n[t] = 0;\n" +
			"        for (t = 0; t < 8 * e.length; t += 8)\n" +
			"            n[t >> 5] |= (255 & e.charCodeAt(t / 8)) << t % 32;\n" +
			"        return n\n" +
			"    }\n" +
			"    function f(e) {\n" +
			"        return c(s(l(e), 8 * e.length))\n" +
			"    }\n" +
			"    function p(e, t) {\n" +
			"        var n, r, o = l(e), i = [], a = [];\n" +
			"        for (i[15] = a[15] = void 0,\n" +
			"             o.length > 16 && (o = s(o, 8 * e.length)),\n" +
			"                 n = 0; 16 > n; n += 1)\n" +
			"            i[n] = 909522486 ^ o[n],\n" +
			"                a[n] = 1549556828 ^ o[n];\n" +
			"        return r = s(i.concat(l(t)), 512 + 8 * t.length),\n" +
			"            c(s(a.concat(r), 640))\n" +
			"    }\n" +
			"    function d(e) {\n" +
			"        var t, n, r = \"0123456789abcdef\", o = \"\";\n" +
			"        for (n = 0; n < e.length; n += 1)\n" +
			"            t = e.charCodeAt(n),\n" +
			"                o += r.charAt(t >>> 4 & 15) + r.charAt(15 & t);\n" +
			"        return o\n" +
			"    }\n" +
			"    function h(e) {\n" +
			"        return unescape(encodeURIComponent(e))\n" +
			"    }\n" +
			"    function m(e) {\n" +
			"        return f(h(e))\n" +
			"    }\n" +
			"    function g(e) {\n" +
			"        return d(m(e))\n" +
			"    }\n" +
			"    function v(e, t) {\n" +
			"        return p(h(e), h(t))\n" +
			"    }\n" +
			"    function y(e, t) {\n" +
			"        return d(v(e, t))\n" +
			"    }\n" +
			"    function b(e, t, n) {\n" +
			"        return t ? n ? v(t, e) : y(t, e) : n ? m(e) : g(e)\n" +
			"    }\n" +
			"    \"function\" == typeof define && define.amd ? define(\"static/js/lib/md5\", [\"require\"], function() {\n" +
			"        return b\n" +
			"    }) : \"object\" == typeof module && module.exports ? module.exports = b : e.md5 = b\n" +
			"}(this)";

}
