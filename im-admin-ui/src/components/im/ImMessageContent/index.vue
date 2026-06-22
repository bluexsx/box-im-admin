<template>
	<div>
		<div v-if="message.type == 1">
			<image-preview :src="JSON.parse(message.content).thumbUrl" :full-src="JSON.parse(message.content).originUrl"
				:width="80" :height="80" />
		</div>
		<div v-else-if="message.type == 2">
			<el-link type="primary" :href="JSON.parse(message.content).url" :download="JSON.parse(message.content).name"
				target="_blank">
				{{ JSON.parse(message.content).name }}
			</el-link>
		</div>
		<div v-else-if="message.type == 3">
			<audio style="height: 40px;" controls :src="JSON.parse(message.content).url"></audio>
		</div>
		<div v-else-if="message.type == 4">
			<video style="max-height: 120px;max-width: 200px;" controls :poster="JSON.parse(message.content).coverUrl"
				:src="JSON.parse(message.content).videoUrl"></video>
		</div>
		<div v-else class="message-text" v-html="transform(replaceURLWithHTMLLinks(html2Escape(message.content)))">
		</div>
	</div>
</template>

<script setup lang="ts" name="ImMessageContent">
import { ref } from 'vue'
import { transform } from "@/utils/emotion"
import { replaceURLWithHTMLLinks } from "@/utils/url"
import { html2Escape } from "@/utils/str"

const props = defineProps({
	message: {
		type: Object,
		required: true
	}
})
</script>

<style scoped>
.link {
	color: inherit;
	text-decoration: none;
}

.message-text {
	display: inline-flex;
	align-items: center;
	white-space: pre-wrap;
	word-break: break-word;

	:deep(.emoji-small) {
		width: 20px;
		height: 20px;
		vertical-align: bottom;
	}
}
</style>
