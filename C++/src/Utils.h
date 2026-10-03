#pragma once
// Small helpers that replace Java library features (String.trim, equalsIgnoreCase,
// Double.toString, LocalDate.now, String.format("%.1f"), ...).
#include <algorithm>
#include <cctype>
#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <string>
#include <vector>

namespace util
{

    inline std::string trim(const std::string &s)
    {
        size_t b = 0, e = s.size();
        while (b < e && static_cast<unsigned char>(s[b]) <= ' ')
            ++b;
        while (e > b && static_cast<unsigned char>(s[e - 1]) <= ' ')
            --e;
        return s.substr(b, e - b);
    }

    inline std::string toLower(std::string s)
    {
        for (char &c : s)
            c = static_cast<char>(std::tolower(static_cast<unsigned char>(c)));
        return s;
    }

    inline std::string toUpper(std::string s)
    {
        for (char &c : s)
            c = static_cast<char>(std::toupper(static_cast<unsigned char>(c)));
        return s;
    }

    inline bool equalsIgnoreCase(const std::string &a, const std::string &b)
    {
        return toLower(a) == toLower(b);
    }

    inline std::string repeat(const std::string &s, int n)
    {
        std::string out;
        for (int i = 0; i < n; ++i)
            out += s;
        return out;
    }

    // Shortens long text so it doesn't break table alignment.
    inline std::string truncate(const std::string &text, size_t maxLength)
    {
        if (text.size() <= maxLength)
            return text;
        return text.substr(0, maxLength - 3) + "...";
    }

    // Same as String.format("%.1f", v)
    inline std::string fixed1(double v)
    {
        char buf[64];
        std::snprintf(buf, sizeof buf, "%.1f", v);
        return buf;
    }

    // Prints a double the way Java's string concatenation does (65000 -> "65000.0",
    // 12.5 -> "12.5"). Uses the shortest representation that round-trips.
    inline std::string num(double v)
    {
        if (std::isnan(v))
            return "NaN";
        if (std::isinf(v))
            return v > 0 ? "Infinity" : "-Infinity";
        char buf[64];
        for (int p = 1; p <= 17; ++p)
        {
            std::snprintf(buf, sizeof buf, "%.*f", p, v);
            if (std::strtod(buf, nullptr) == v)
                return buf;
        }
        std::snprintf(buf, sizeof buf, "%.17g", v);
        return buf;
    }

    // Equivalent of LocalDate.now().toString() -> "YYYY-MM-DD"
    inline std::string today()
    {
        std::time_t t = std::time(nullptr);
        std::tm tmv{};
#ifdef _WIN32
        localtime_s(&tmv, &t);
#else
        localtime_r(&t, &tmv);
#endif
        char buf[16];
        std::strftime(buf, sizeof buf, "%Y-%m-%d", &tmv);
        return buf;
    }

    // Equivalent of ArrayList.remove(Object): removes the first match, returns true if found.
    template <class T>
    bool removeOne(std::vector<T> &v, const T &x)
    {
        auto it = std::find(v.begin(), v.end(), x);
        if (it == v.end())
            return false;
        v.erase(it);
        return true;
    }

} // namespace util
