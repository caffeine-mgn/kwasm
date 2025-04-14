package pw.binom.wit.readers

import pw.binom.io.file.File
import pw.binom.io.file.readText
import pw.binom.wit.parser.BasicTokenizerTest
import pw.binom.wit.writers.PrettyTextWriter
import pw.binom.wit.writers.WitWriter
import kotlin.js.JsFileName
import kotlin.test.Test

class WitReaderTest {
    val text = """
        package binom:pipeline@0.2.5;

        interface types {
            type dimension = u32;
            record point {
                x: dimension,
                y: dimension,
            }
        }

        interface printer {
            type dimension = u32;
            use types.{dimension, point};
            print: func(text: string, arg:u32);
            enum color {
                hot-pink,
                lime-green,
                navy-blue,
            }
            record image {
            	name: u32,
            }
            resource blob {
                constructor(init: list<u32>);
                write: func(bytes: list<u8>);
                read: func(n: u32) -> list<u8>;
                merge: static func(lhs: blob, rhs: blob) -> blob;
            }
        }



        world imports {
            import printer;
            export main: func(args: list<string>) -> (count:u32,value:u32);
        }
    """.trimIndent()

    @Test
    fun parseTest() {
//        val text = File("/tmp/ff/wasi-http/wit/proxy.wit").readText()
//        val text = File("/tmp/ff/wasi-http/wit/handler.wit").readText()
        val text = File("/tmp/ff/wasi-http/wit/types.wit").readText()
        val sb = StringBuilder()
        val w = PrettyTextWriter(sb)
        try {
            WitReader.parse(BasicTokenizerTest.StringTokenizer(text), WitWriter(w))
        } finally {
            println(sb.toString())
        }
    }
}