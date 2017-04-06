#include <iostream>
#include <fstream>
#include <sstream>
#include <GLES3/gl3.h>

namespace gllib{
    class Shader{

        Shader(const GLchar* vsPath, const GLchar * fragPath){

            std::string vsCode;
            std::string fragCode;
            std::ifstream vsShaderFile;
            std::ifstream fragShaderFile;
            // make sure ifstream can throw exception
            vsShaderFile.exceptions(std::ifstream::badbit);
            fragShaderFile.exceptions(std::ifstream::badbit);

            try {
                vsShaderFile.open(vsPath);
                fragShaderFile.open(fragPath);
                std::stringstream vsShaderStream,fragShaderStream;

                vsShaderStream << vsShaderFile.rdbuf();
                fragShaderStream << fragShaderFile.rdbuf();

                vsShaderFile.close();
                fragShaderFile.close();

                vsCode = vsShaderStream.str();
                fragCode = fragShaderStream.str();
            }catch (std::ifstream::failure failure){
                std::cout << "ERROR::SHADER::FILE_NOT_SUCCESSFULY_READ" << std::endl;
            }

            const GLchar* vShaderCode = vsCode.c_str();
            const GLchar* fragShaderCode = fragCode.c_str();

        }

    };
}


